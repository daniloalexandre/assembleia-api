package br.com.example.integration.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.example.dto.AgendaRequestDTO;
import br.com.example.dto.AgendaResponseDTO;
import br.com.example.exception.ErrorResponse;
import br.com.example.model.Agenda;
import br.com.example.repository.AgendaRepository;

@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
public class AgendaControllerTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private AgendaRepository repository;

	private ObjectMapper objectMapper;

	private MockMvc mvc;

	@BeforeAll
	public void setup() {

		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();

	}

	@Transactional
	@Test
	void testGetAll() throws Exception {
		// Setup
		Agenda agenda = new Agenda();
		agenda.setDescription("Pauta teste");
		agenda = repository.save(agenda);

		// When
		MockHttpServletResponse response = mvc.perform(get("/agendas")).andReturn().getResponse();

		// Then
		AgendaResponseDTO[] responseObjects = objectMapper.readValue(response.getContentAsByteArray(),
				AgendaResponseDTO[].class);

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(responseObjects).isNotEmpty();

	}

	@Transactional
	@Test
	void testGetById() throws Exception {
		// Setup
		String description = "Pauta teste";

		Agenda agenda = new Agenda();
		agenda.setDescription(description);
		agenda = repository.save(agenda);

		int idRequested = agenda.getId();

		// When
		MockHttpServletResponse response = mvc.perform(get("/agendas/" + idRequested)).andReturn().getResponse();

		// Then
		AgendaResponseDTO agendaResponseDTO = objectMapper.readValue(response.getContentAsByteArray(),
				AgendaResponseDTO.class);

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(agendaResponseDTO).isNotNull();
		assertThat(agendaResponseDTO.getId()).isEqualTo(idRequested);
		assertThat(agendaResponseDTO.getDescription()).isEqualTo(description);

	}

	@Transactional
	@Test
	void testGetByIdResourceNotFound() throws Exception {
		// Setup
		int idRequested = 100;

		// When
		MockHttpServletResponse response = mvc.perform(get("/agendas/" + idRequested)).andReturn().getResponse();

		// Then
		ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsByteArray(), ErrorResponse.class);

		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
		assertThat(errorResponse).isNotNull();

	}

	@Transactional
	@Test
	void testSave() throws Exception {
		// Setup
		String description = "Pauta teste";
		AgendaRequestDTO requestDTO = new AgendaRequestDTO();
		requestDTO.setDescription(description);

		// When
		MockHttpServletResponse response = mvc.perform(post("/agendas").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestDTO))).andReturn().getResponse();

		// Then
		AgendaResponseDTO agendaResponseDTO = objectMapper.readValue(response.getContentAsByteArray(),
				AgendaResponseDTO.class);

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(agendaResponseDTO).isNotNull();
		assertThat(agendaResponseDTO.getId()).isPositive();
		assertThat(agendaResponseDTO.getDescription()).isEqualTo(description);

	}

	@Transactional
	@Test
	void testSaveMethodArgumentNotValid() throws Exception {
		// Setup
		AgendaRequestDTO requestDTO = new AgendaRequestDTO();
		requestDTO.setDescription(null);

		// When
		MockHttpServletResponse response = mvc.perform(post("/agendas").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestDTO))).andReturn().getResponse();

		// Then
		ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsByteArray(), ErrorResponse.class);

		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		assertThat(errorResponse).isNotNull();

	}

	@Transactional
	@Test
	void testDelete() throws Exception {
		// Setup
		String description = "Pauta teste";

		Agenda agenda = new Agenda();
		agenda.setDescription(description);
		agenda = repository.save(agenda);

		int idRequested = agenda.getId();

		// When
		MockHttpServletResponse response = mvc.perform(delete("/agendas/" + idRequested)).andReturn().getResponse();

		// Then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

		// When
		response = mvc.perform(get("/agendas/" + idRequested)).andReturn().getResponse();

		// Then
		ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsByteArray(), ErrorResponse.class);

		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
		assertThat(errorResponse).isNotNull();

	}

	@Transactional
	@Test
	void testDeleteResourceNotFound() throws Exception {
		// Setup
		int idRequested = 100;

		// When
		MockHttpServletResponse response = mvc.perform(delete("/agendas/" + idRequested)).andReturn().getResponse();

		// Then
		ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsByteArray(), ErrorResponse.class);

		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
		assertThat(errorResponse).isNotNull();

	}

}
