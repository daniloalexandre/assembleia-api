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

import br.com.example.dto.AgendaRequestDTO;
import br.com.example.dto.AgendaResponseDTO;
import br.com.example.exception.ResourceNotFoundException;
import br.com.example.mapper.AgendaMapper;
import br.com.example.model.Agenda;
import br.com.example.service.AgendaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Controlador responsável por processar as requisições relacinadas as pautas.
 * 
 * BasePath: /agendas
 * 
 * @author Danilo Alexandre
 *
 */
@Slf4j
@Api
@RestController
@RequestMapping(value = "/agendas")
public class AgendaController {

	@Autowired
	private AgendaService service;

	@ApiOperation(value = "Lista as pautas existentes", produces = MediaType.APPLICATION_JSON_VALUE, protocols = "http")
	@GetMapping
	public ResponseEntity<List<AgendaResponseDTO>> getAll() {
		log.info("getAll");

		return ResponseEntity.ok(service.getAll().stream()
				.map((Agenda pauta) -> AgendaMapper.INSTANCE.toResponseDTO(pauta)).collect(Collectors.toList()));
	}

	@ApiOperation(value = "Busca pautas pelo id", produces = MediaType.APPLICATION_JSON_VALUE, protocols = "http")
	@GetMapping(value = "/{id}")
	public ResponseEntity<AgendaResponseDTO> getById(@PathVariable int id) throws ResourceNotFoundException {
		log.info(String.format("getById [ id: %s ]", id));

		Agenda agenda = service.get(id);
		AgendaResponseDTO agendaResponseDTO = AgendaMapper.INSTANCE.toResponseDTO(agenda);
		return ResponseEntity.ok(agendaResponseDTO);

	}

	@ApiOperation(value = "Armazena uma nova pauta", produces = MediaType.APPLICATION_JSON_VALUE, protocols = "http")
	@PostMapping
	public ResponseEntity<AgendaResponseDTO> save(@Valid @RequestBody AgendaRequestDTO pautaRequestDTO) {
		log.info(String.format("save [ description: %s ]", pautaRequestDTO.getDescription()));

		Agenda agenda = AgendaMapper.INSTANCE.toEntity(pautaRequestDTO);
		agenda = service.save(agenda);
		AgendaResponseDTO agendaResponseDTO = AgendaMapper.INSTANCE.toResponseDTO(agenda);
		return ResponseEntity.ok(agendaResponseDTO);

	}

	@ApiOperation(value = "Remove pautas pelo id", produces = MediaType.APPLICATION_JSON_VALUE, protocols = "http")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) throws ResourceNotFoundException {
		log.info(String.format("delete [ id: %s ]", id));

		service.delete(id);
		return ResponseEntity.ok().build();

	}

}
