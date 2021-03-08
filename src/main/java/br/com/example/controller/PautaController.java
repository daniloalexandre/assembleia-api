package br.com.example.controller;

import java.util.ArrayList;
import java.util.List;

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

import br.com.example.dto.PautaRequestDTO;
import br.com.example.dto.PautaResponseDTO;
import br.com.example.exception.ResourceNotFoundException;
import br.com.example.mapper.PautaMapper;
import br.com.example.model.Pauta;
import br.com.example.service.PautaService;
/**
 * Controlador responsável por processar as requisições relacinadas as pautas.
 * 
 * BasePath: /pautas
 * 
 * @author Danilo Alexandre
 *
 */
@RestController
@RequestMapping(value = "/pautas")
public class PautaController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PautaService service;

	@GetMapping
	public ResponseEntity<List<PautaResponseDTO>> getAll() {

		List<Pauta> pautas = service.getAll();
		List<PautaResponseDTO> pautaResponseDTOs = new ArrayList<PautaResponseDTO>();
		pautas.forEach(pauta -> {
			pautaResponseDTOs.add(PautaMapper.INSTANCE.toResponseDTO(pauta));
		});
		return ResponseEntity.ok(pautaResponseDTOs);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<PautaResponseDTO> getById(@PathVariable int id) throws ResourceNotFoundException {

		Pauta pauta = service.get(id);
		PautaResponseDTO pautaResponseDTO = PautaMapper.INSTANCE.toResponseDTO(pauta);
		return ResponseEntity.ok(pautaResponseDTO);

	}

	@PostMapping
	public ResponseEntity<PautaResponseDTO> save(@Valid @RequestBody PautaRequestDTO pautaRequestDTO) {

		Pauta pauta = PautaMapper.INSTANCE.toEntity(pautaRequestDTO);
		pauta = service.save(pauta);
		PautaResponseDTO pautaResponseDTO = PautaMapper.INSTANCE.toResponseDTO(pauta);
		return ResponseEntity.ok(pautaResponseDTO);

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) throws ResourceNotFoundException {
		
		service.delete(id);
		return ResponseEntity.ok().build();

	}

}
