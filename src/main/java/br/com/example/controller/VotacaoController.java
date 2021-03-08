package br.com.example.controller;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.example.async.VotacaoTasks;
import br.com.example.dto.VotacaoRequestDTO;
import br.com.example.dto.VotacaoResponseDTO;
import br.com.example.dto.VotacaoResultadoResponseDTO;
import br.com.example.dto.VotoRequestDTO;
import br.com.example.dto.VotoResponseDTO;
import br.com.example.exception.AccessDeniedException;
import br.com.example.exception.DataIntegrityException;
import br.com.example.exception.ResourceNotFoundException;
import br.com.example.exception.ThirdPartException;
import br.com.example.exception.UnbaleVoteException;
import br.com.example.mapper.VotacaoMapper;
import br.com.example.mapper.VotoMapper;
import br.com.example.model.Pauta;
import br.com.example.model.Votacao;
import br.com.example.model.Voto;
import br.com.example.service.PautaService;
import br.com.example.service.VotacaoService;
import br.com.example.service.VotoService;

/**
 * Controlador responsável por processar as requisições relativas à votação e
 * aos votos.
 * 
 * @author Danilo Alexandre
 *
 */
@RestController
@RequestMapping(value = "/votacoes")
public class VotacaoController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PautaService pautaService;

	@Autowired
	private VotacaoService votacaoService;

	@Autowired
	private VotoService votoService;

	@Autowired
	private VotacaoTasks votacaoTask;

	@GetMapping
	public ResponseEntity<List<VotacaoResponseDTO>> getAll() {

		List<Votacao> votacaos = votacaoService.getAll();
		List<VotacaoResponseDTO> votacaoResponseDTOs = new ArrayList<VotacaoResponseDTO>();
		votacaos.forEach(votacao -> {
			votacaoResponseDTOs.add(VotacaoMapper.INSTANCE.toResponseDTO(votacao));
		});
		return ResponseEntity.ok(votacaoResponseDTOs);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<VotacaoResponseDTO> getById(@PathVariable int id) throws ResourceNotFoundException {

		Votacao votacao = votacaoService.get(id);
		VotacaoResponseDTO votacaoResponseDTO = VotacaoMapper.INSTANCE.toResponseDTO(votacao);
		return ResponseEntity.ok(votacaoResponseDTO);
	}

	@PostMapping
	public ResponseEntity<VotacaoResponseDTO> save(@Valid @RequestBody VotacaoRequestDTO votacaoRequestDTO)
			throws ResourceNotFoundException {
		Pauta pauta = pautaService.get(votacaoRequestDTO.getIdPauta());
		Votacao votacao = votacaoService.save(Votacao.createVotacao(pauta, votacaoRequestDTO.getDuracao()));
		votacaoTask.processarResultadoAfterMinutes(votacao, votacaoRequestDTO.getDuracao());
		VotacaoResponseDTO votacaoResponseDTO = VotacaoMapper.INSTANCE.toResponseDTO(votacao);
		return ResponseEntity.ok(votacaoResponseDTO);

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) throws ResourceNotFoundException {

		votacaoService.delete(id);
		return ResponseEntity.ok().build();

	}

	@GetMapping(value = "/{id}/votos")
	public ResponseEntity<List<VotoResponseDTO>> getVotosById(@PathVariable int id) {

		List<Voto> votos = votoService.getByVotacaoId(id);
		List<VotoResponseDTO> votoResponseDTOs = new ArrayList<VotoResponseDTO>();
		votos.forEach(voto -> {
			votoResponseDTOs.add(VotoMapper.INSTANCE.toResponseDTO(voto));
		});
		return ResponseEntity.ok(votoResponseDTOs);
	}

	@PostMapping(value = "/{id}/votos")
	public ResponseEntity<VotoResponseDTO> saveVoto(@Valid @RequestBody VotoRequestDTO votoRequestDTO,
			@PathVariable int id)
			throws ResourceNotFoundException, DataIntegrityException, UnbaleVoteException, ThirdPartException {

		Votacao votacao = votacaoService.get(id);
		Voto voto = VotoMapper.INSTANCE.toEntity(votoRequestDTO);
		voto.setVotacao(votacao);
		voto.setTimestamp(LocalDateTime.now());
		voto = votoService.save(voto);
		VotoResponseDTO votoResponseDTO = VotoMapper.INSTANCE.toResponseDTO(voto);
		return ResponseEntity.ok(votoResponseDTO);

	}

	@GetMapping(value = "/{id}/resultado")
	public ResponseEntity<VotacaoResultadoResponseDTO> getResultadoById(@PathVariable int id)
			throws ResourceNotFoundException, AccessDeniedException {

		Votacao votacao = votacaoService.get(id);
		Map<Boolean, BigInteger> resultado = votoService.countVotos(votacao);
		VotacaoResponseDTO votacaoResponseDTO = VotacaoMapper.INSTANCE.toResponseDTO(votacao);
		VotacaoResultadoResponseDTO votacaoResultadoResponseDTO = new VotacaoResultadoResponseDTO();
		votacaoResultadoResponseDTO.setVotacao(votacaoResponseDTO);
		votacaoResultadoResponseDTO
				.setFavoravel(resultado.get(Boolean.TRUE) != null ? resultado.get(Boolean.TRUE) : new BigInteger("0"));
		votacaoResultadoResponseDTO.setDesfavoravel(
				resultado.get(Boolean.FALSE) != null ? resultado.get(Boolean.FALSE) : new BigInteger("0"));

		return ResponseEntity.ok(votacaoResultadoResponseDTO);
	}

}
