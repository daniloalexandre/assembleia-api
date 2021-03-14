package br.com.example.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.example.dto.VoteRequestDTO;
import br.com.example.dto.VoteResponseDTO;
import br.com.example.dto.VotingRequestDTO;
import br.com.example.dto.VotingResponseDTO;
import br.com.example.dto.VotingResultResponseDTO;
import br.com.example.exception.DataIntegrityException;
import br.com.example.exception.ResourceNotFoundException;
import br.com.example.exception.ThirdPartException;
import br.com.example.exception.UnbaleVoteException;
import br.com.example.mapper.VoteMapper;
import br.com.example.mapper.VotingMapper;
import br.com.example.model.Agenda;
import br.com.example.model.Vote;
import br.com.example.model.Voting;
import br.com.example.service.AgendaService;
import br.com.example.service.VoteService;
import br.com.example.service.VotingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Controlador responsável por processar as requisições relativas à votação e
 * aos votos.
 * 
 * @author Danilo Alexandre
 *
 */
@Slf4j
@Api
@RestController
@RequestMapping(value = "/votings")
public class VotingController {

	@Autowired
	private AgendaService agendaService;

	@Autowired
	private VotingService votingService;

	@Autowired
	private VoteService voteService;

	@ApiOperation(value = "Lista as votações existentes", produces = MediaType.APPLICATION_JSON_VALUE, protocols = "http")
	@GetMapping
	public ResponseEntity<List<VotingResponseDTO>> getAll() {
		log.info(String.format("getAll"));

		return ResponseEntity.ok(votingService.getAll().stream()
				.map((Voting votacao) -> VotingMapper.INSTANCE.toResponseDTO(votacao)).collect(Collectors.toList()));
	}

	@ApiOperation(value = "Busca votações pelo id", produces = MediaType.APPLICATION_JSON_VALUE, protocols = "http")
	@GetMapping(value = "/{id}")
	public ResponseEntity<VotingResponseDTO> getById(@PathVariable int id) throws ResourceNotFoundException {
		log.info(String.format("getById [ id: %s ]", id));

		Voting voting = votingService.get(id);
		VotingResponseDTO votacaoResponseDTO = VotingMapper.INSTANCE.toResponseDTO(voting);
		return ResponseEntity.ok(votacaoResponseDTO);
	}

	@ApiOperation(value = "Armazena uma nova votação", produces = MediaType.APPLICATION_JSON_VALUE, protocols = "http")
	@PostMapping
	public ResponseEntity<VotingResponseDTO> save(@Valid @RequestBody VotingRequestDTO votingRequestDTO)
			throws ResourceNotFoundException {
		log.info(String.format("save [ id_pauta: %s, duracao: %s ]", votingRequestDTO.getIdAgenda(),
				votingRequestDTO.getDuration()));

		Agenda agenda = agendaService.get(votingRequestDTO.getIdAgenda());
		Voting voting = votingService.save(agenda, votingRequestDTO.getDuration());
		VotingResponseDTO votingResponseDTO = VotingMapper.INSTANCE.toResponseDTO(voting);
		return ResponseEntity.ok(votingResponseDTO);

	}

	@ApiOperation(value = "Remove votações pelo id", produces = MediaType.APPLICATION_JSON_VALUE, protocols = "http")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) throws ResourceNotFoundException {
		log.info(String.format("delete [ id: %s ]", id));

		votingService.delete(id);
		return ResponseEntity.ok().build();

	}

	@ApiOperation(value = "Lista todos os votos pelo id da votação", produces = MediaType.APPLICATION_JSON_VALUE, protocols = "http")
	@GetMapping(value = "/{id}/votes")
	public ResponseEntity<List<VoteResponseDTO>> getVotosById(@PathVariable int id) {

		return ResponseEntity.ok(voteService.getByVotingId(id).stream()
				.map((Vote vote) -> VoteMapper.INSTANCE.toResponseDTO(vote)).collect(Collectors.toList()));
	}

	@ApiOperation(value = "Armazena votos pelo id da votação", produces = MediaType.APPLICATION_JSON_VALUE, protocols = "http")
	@PostMapping(value = "/{id}/votes")
	public ResponseEntity<VoteResponseDTO> saveVoto(@Valid @RequestBody VoteRequestDTO voteRequestDTO,
			@PathVariable int id)
			throws ResourceNotFoundException, DataIntegrityException, UnbaleVoteException, ThirdPartException {

		Voting voting = votingService.get(id);
		Vote vote = VoteMapper.INSTANCE.toEntity(voteRequestDTO);
		vote = voteService.save(voting, vote);
		VoteResponseDTO votoResponseDTO = VoteMapper.INSTANCE.toResponseDTO(vote);
		return ResponseEntity.ok(votoResponseDTO);

	}

	@ApiOperation(value = "Busca resultados de votação pelo id", produces = MediaType.APPLICATION_JSON_VALUE, protocols = "http")
	@GetMapping(value = "/{id}/result")
	public ResponseEntity<VotingResultResponseDTO> getResultadoById(@PathVariable int id)
			throws ResourceNotFoundException {

		Voting voting = votingService.get(id);
		VotingResponseDTO votingResponseDTO = VotingMapper.INSTANCE.toResponseDTO(voting);

		VotingResultResponseDTO votingResultResponseDTO = new VotingResultResponseDTO();
		votingResultResponseDTO.setVoting(votingResponseDTO);
		votingResultResponseDTO.setFavorable(voteService.countByFavorableAndVotingId(true, id));
		votingResultResponseDTO.setUnfavorable(voteService.countByFavorableAndVotingId(false, id));

		return ResponseEntity.ok(votingResultResponseDTO);
	}

}
