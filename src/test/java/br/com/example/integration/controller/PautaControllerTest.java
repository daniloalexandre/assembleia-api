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

import br.com.example.dto.PautaRequestDTO;
import br.com.example.dto.PautaResponseDTO;
import br.com.example.exception.ErrorResponse;
import br.com.example.model.Pauta;
import br.com.example.repository.PautaRepository;

@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
public class PautaControllerTest {
	
	@Autowired
    private WebApplicationContext context;
	
	@Autowired
	private PautaRepository repository;
	
	private ObjectMapper objectMapper;

    private MockMvc mvc;

    @BeforeAll
    public void setup() {
    	
    	objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        
    }
    
    @Transactional
    @Test
    void testGetAll() throws Exception {
    	//Setup
    	Pauta pauta = new Pauta();
    	pauta.setDescricao("Pauta teste");
    	pauta = repository.save(pauta);
    	
    	//When
    	 MockHttpServletResponse response = mvc.perform(get("/pautas")).andReturn().getResponse();
    	
    	//Then
    	 PautaResponseDTO[] responseObjects = objectMapper.readValue(response.getContentAsByteArray(), PautaResponseDTO[].class);

    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(responseObjects).isNotEmpty();
		
    }
    
    
    @Transactional
    @Test
    void testGetById() throws Exception {
    	//Setup
    	String descricao = "Pauta teste";
    	
    	Pauta pauta = new Pauta();
    	pauta.setDescricao(descricao);
    	pauta = repository.save(pauta);
    	
    	int idRequested = pauta.getId();
    	
    	//When
    	 MockHttpServletResponse response = mvc.perform(get("/pautas/"+idRequested)).andReturn().getResponse();
    	
    	//Then
    	PautaResponseDTO pautaResponseDTO = objectMapper.readValue(response.getContentAsByteArray(), PautaResponseDTO.class);

    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    	assertThat(pautaResponseDTO).isNotNull();
		assertThat(pautaResponseDTO.getId()).isEqualTo(idRequested);
		assertThat(pautaResponseDTO.getDescricao()).isEqualTo(descricao);
		
    }
    
    @Transactional
    @Test
    void testGetByIdResourceNotFound() throws Exception {
    	//Setup
    	int idRequested = 100;
    	
    	//When
    	MockHttpServletResponse response = mvc.perform(get("/pautas/"+idRequested)).andReturn().getResponse();
    	
    	//Then
    	ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsByteArray(), ErrorResponse.class);

    	assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    	assertThat(errorResponse).isNotNull();
		
    }
    
    
    @Transactional
    @Test
    void testSave() throws Exception {
    	//Setup
    	String descricao = "Pauta teste";
    	PautaRequestDTO requestDTO = new PautaRequestDTO();
    	requestDTO.setDescricao(descricao);
    	
    	//When
    	 MockHttpServletResponse response = mvc.perform(post("/pautas").contentType(MediaType.APPLICATION_JSON)
     			.content(objectMapper.writeValueAsString(requestDTO))).andReturn().getResponse();
    	
    	//Then
    	PautaResponseDTO pautaResponseDTO = objectMapper.readValue(response.getContentAsByteArray(), PautaResponseDTO.class);

    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    	assertThat(pautaResponseDTO).isNotNull();
		assertThat(pautaResponseDTO.getId()).isPositive();
		assertThat(pautaResponseDTO.getDescricao()).isEqualTo(descricao);
		
    }
    
    
    @Transactional
    @Test
    void testSaveMethodArgumentNotValid() throws Exception {
    	//Setup
    	PautaRequestDTO requestDTO = new PautaRequestDTO();
    	requestDTO.setDescricao(null);
    	
    	//When
    	 MockHttpServletResponse response = mvc.perform(post("/pautas").contentType(MediaType.APPLICATION_JSON)
      			.content(objectMapper.writeValueAsString(requestDTO))).andReturn().getResponse();
    	
    	//Then
    	 ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsByteArray(), ErrorResponse.class);

    	assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    	assertThat(errorResponse).isNotNull();
		
    }
    
    @Transactional
    @Test
    void testDelete() throws Exception {
    	//Setup
    	String descricao = "Pauta teste";
    	
    	Pauta pauta = new Pauta();
    	pauta.setDescricao(descricao);
    	pauta = repository.save(pauta);
    	
    	int idRequested = pauta.getId();
    	
    	//When
    	 MockHttpServletResponse response = mvc.perform(delete("/pautas/"+idRequested)).andReturn().getResponse();
    	
    	//Then
    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    	
    	
    	//When
    	response = mvc.perform(get("/pautas/"+idRequested)).andReturn().getResponse();
    	
    	//Then
    	ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsByteArray(), ErrorResponse.class);

    	assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    	assertThat(errorResponse).isNotNull();

		
    }
    
    
    @Transactional
    @Test
    void testDeleteResourceNotFound() throws Exception {
    	//Setup
    	int idRequested = 100;
    	
    	//When
    	MockHttpServletResponse response = mvc.perform(delete("/pautas/"+idRequested)).andReturn().getResponse();
    	
    	//Then
    	ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsByteArray(), ErrorResponse.class);

    	assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    	assertThat(errorResponse).isNotNull();
		
    }
    

}
