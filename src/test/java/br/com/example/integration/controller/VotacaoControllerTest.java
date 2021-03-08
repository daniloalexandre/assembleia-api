package br.com.example.integration.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;

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

import br.com.example.dto.VotacaoRequestDTO;
import br.com.example.dto.VotacaoResponseDTO;
import br.com.example.dto.VotacaoResultadoResponseDTO;
import br.com.example.dto.VotoResponseDTO;
import br.com.example.exception.ErrorResponse;
import br.com.example.model.Pauta;
import br.com.example.model.Votacao;
import br.com.example.model.Voto;
import br.com.example.repository.PautaRepository;
import br.com.example.repository.VotacaoRepository;
import br.com.example.repository.VotoRepository;

@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
public class VotacaoControllerTest {
	
	@Autowired
    private WebApplicationContext context;
	
	@Autowired
	private PautaRepository pautaRepository;
	
	@Autowired
	private VotacaoRepository votacaoRepository;
	
	@Autowired
	private VotoRepository votoRepository;
	
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
    	pauta = pautaRepository.save(pauta);
    	
    	Votacao votacao = Votacao.createVotacao(pauta, 5);
    	votacao = votacaoRepository.save(votacao);
    	
    	//When
    	 MockHttpServletResponse response = mvc.perform(get("/votacoes")).andReturn().getResponse();
    	
    	//Then
    	 VotacaoResponseDTO[] responseObjects = objectMapper.readValue(response.getContentAsByteArray(), VotacaoResponseDTO[].class);

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
    	pauta = pautaRepository.save(pauta);
    	
    	Votacao votacao = Votacao.createVotacao(pauta, 5);
    	votacao = votacaoRepository.save(votacao);
    	
    	int idRequested = votacao.getId();
    	
    	//When
    	 MockHttpServletResponse response = mvc.perform(get("/votacoes/"+idRequested)).andReturn().getResponse();
    	
    	//Then
    	 VotacaoResponseDTO votacaoResponseDTO = objectMapper.readValue(response.getContentAsByteArray(), VotacaoResponseDTO.class);

    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    	assertThat(votacaoResponseDTO).isNotNull();
		assertThat(votacaoResponseDTO.getId()).isEqualTo(idRequested);
		
    }
    
    @Transactional
    @Test
    void testGetByIdResourceNotFound() throws Exception {
    	//Setup
    	int idRequested = 100;
    	
    	//When
    	MockHttpServletResponse response = mvc.perform(get("/votacoes/"+idRequested)).andReturn().getResponse();
    	
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
    	
    	Pauta pauta = new Pauta();
    	pauta.setDescricao(descricao);
    	pauta = pautaRepository.save(pauta);
    	
    	int idPauta = pauta.getId();
    	VotacaoRequestDTO requestDTO = new VotacaoRequestDTO();
    	requestDTO.setDuracao(5);
    	requestDTO.setIdPauta(idPauta);
    	
    	
    	//When
    	 MockHttpServletResponse response = mvc.perform(post("/votacoes").contentType(MediaType.APPLICATION_JSON)
     			.content(objectMapper.writeValueAsString(requestDTO))).andReturn().getResponse();
    	
    	//Then
    	 VotacaoResponseDTO votacaoResponseDTO = objectMapper.readValue(response.getContentAsByteArray(), VotacaoResponseDTO.class);

    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    	assertThat(votacaoResponseDTO).isNotNull();
		assertThat(votacaoResponseDTO.getId()).isPositive();
		assertThat(votacaoResponseDTO.getPauta().getId()).isEqualTo(idPauta);
		
    }
    
    
    @Transactional
    @Test
    void testSaveMethodArgumentNotValid() throws Exception {
    	//Setup
    	VotacaoRequestDTO requestDTO = new VotacaoRequestDTO();
    	requestDTO.setIdPauta(0);
    	
    	//When
    	 MockHttpServletResponse response = mvc.perform(post("/votacoes").contentType(MediaType.APPLICATION_JSON)
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
    	pauta = pautaRepository.save(pauta);
    	
    	Votacao votacao = Votacao.createVotacao(pauta, 5);
    	votacao = votacaoRepository.save(votacao);
    	
    	int idRequested = votacao.getId();
    	
    	//When
    	 MockHttpServletResponse response = mvc.perform(delete("/votacoes/"+idRequested)).andReturn().getResponse();
    	
    	//Then
    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    	
    	
    	//When
    	response = mvc.perform(get("/votacoes/"+idRequested)).andReturn().getResponse();
    	
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
    	MockHttpServletResponse response = mvc.perform(delete("/votacoes/"+idRequested)).andReturn().getResponse();
    	
    	//Then
    	ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsByteArray(), ErrorResponse.class);

    	assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    	assertThat(errorResponse).isNotNull();
		
    }
    
    
    @Transactional
    @Test
    void testGetVotosById() throws Exception {
    	//Setup
    	Pauta pauta = new Pauta();
    	pauta.setDescricao("Pauta teste");
    	pauta = pautaRepository.save(pauta);
    	
    	Votacao votacao = Votacao.createVotacao(pauta, 5);
    	votacao = votacaoRepository.save(votacao);
    	
    	int idRequested = votacao.getId();
    	
    	Voto voto = new Voto();
    	voto.setCpf("12345678910");
    	voto.setFavoravel(true);
    	voto.setTimestamp(LocalDateTime.now());
    	voto.setVotacao(votacao);
    	
    	voto = votoRepository.save(voto);
    	
    	Voto voto2 = new Voto();
    	voto2.setCpf("12345678910");
    	voto2.setFavoravel(false);
    	voto2.setTimestamp(LocalDateTime.now());
    	voto2.setVotacao(votacao);
    	
    	voto2 = votoRepository.save(voto2);
    	
    	//When
    	 MockHttpServletResponse response = mvc.perform(get("/votacoes/"+idRequested+"/votos")).andReturn().getResponse();
    	
    	//Then
    	 VotoResponseDTO[] responseObjects = objectMapper.readValue(response.getContentAsByteArray(), VotoResponseDTO[].class);

    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(responseObjects).isNotEmpty();
		
    }
    
    @Transactional
    @Test
    void testGetResultadoById() throws Exception {
    	//Setup
    	Pauta pauta = new Pauta();
    	pauta.setDescricao("Pauta teste");
    	pauta = pautaRepository.save(pauta);
    	
    	Votacao votacao = new Votacao();
    	votacao.setPauta(pauta);
    	votacao.setPrazo(LocalDateTime.now().minusMinutes(5));
    	votacao = votacaoRepository.save(votacao);
    	
    	int idRequested = votacao.getId();
    	
    	Voto voto = new Voto();
    	voto.setCpf("12345678910");
    	voto.setFavoravel(true);
    	voto.setTimestamp(LocalDateTime.now());
    	voto.setVotacao(votacao);
    	
    	voto = votoRepository.save(voto);
    	
    	Voto voto2 = new Voto();
    	voto2.setCpf("12345678910");
    	voto2.setFavoravel(false);
    	voto2.setTimestamp(LocalDateTime.now());
    	voto2.setVotacao(votacao);
    	
    	voto2 = votoRepository.save(voto2);
    	
    	//When
    	 MockHttpServletResponse response = mvc.perform(get("/votacoes/"+idRequested+"/resultado")).andReturn().getResponse();
    	
    	//Then
    	 VotacaoResultadoResponseDTO votacaoResultadoResponseDTO = objectMapper.readValue(response.getContentAsByteArray(), VotacaoResultadoResponseDTO.class);

    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(votacaoResultadoResponseDTO.getVotacao().getId()).isEqualTo(idRequested);
		assertThat(votacaoResultadoResponseDTO.getFavoravel()).isEqualTo(1);
		assertThat(votacaoResultadoResponseDTO.getDesfavoravel()).isEqualTo(1);
    }
    
    @Transactional
    @Test
    void testGetResultadoByIdVotacaoNaoFinalizada() throws Exception {
    	//Setup
    	Pauta pauta = new Pauta();
    	pauta.setDescricao("Pauta teste");
    	pauta = pautaRepository.save(pauta);
    	
    	Votacao votacao = Votacao.createVotacao(pauta, 5);
    	votacao = votacaoRepository.save(votacao);
    	
    	int idRequested = votacao.getId();
    	
    	Voto voto = new Voto();
    	voto.setCpf("12345678910");
    	voto.setFavoravel(true);
    	voto.setTimestamp(LocalDateTime.now());
    	voto.setVotacao(votacao);
    	
    	voto = votoRepository.save(voto);
    	
    	Voto voto2 = new Voto();
    	voto2.setCpf("12345678910");
    	voto2.setFavoravel(false);
    	voto2.setTimestamp(LocalDateTime.now());
    	voto2.setVotacao(votacao);
    	
    	voto2 = votoRepository.save(voto2);
    	
    	//When
    	 MockHttpServletResponse response = mvc.perform(get("/votacoes/"+idRequested+"/resultado")).andReturn().getResponse();
    	
    	//Then
    	 ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsByteArray(), ErrorResponse.class);

    	assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    	assertThat(errorResponse).isNotNull();
    }
    
    @Transactional
    @Test
    void testGetResultadoByIdWithoutVotoFavoravel() throws Exception {
    	//Setup
    	Pauta pauta = new Pauta();
    	pauta.setDescricao("Pauta teste");
    	pauta = pautaRepository.save(pauta);
    	
    	Votacao votacao = new Votacao();
    	votacao.setPauta(pauta);
    	votacao.setPrazo(LocalDateTime.now().minusMinutes(5));
    	votacao = votacaoRepository.save(votacao);
    	
    	int idRequested = votacao.getId();

    	Voto voto = new Voto();
    	voto.setCpf("12345678910");
    	voto.setFavoravel(false);
    	voto.setTimestamp(LocalDateTime.now());
    	voto.setVotacao(votacao);
    	
    	voto= votoRepository.save(voto);
    	
    	//When
    	 MockHttpServletResponse response = mvc.perform(get("/votacoes/"+idRequested+"/resultado")).andReturn().getResponse();
    	
    	//Then
    	 VotacaoResultadoResponseDTO votacaoResultadoResponseDTO = objectMapper.readValue(response.getContentAsByteArray(), VotacaoResultadoResponseDTO.class);

    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(votacaoResultadoResponseDTO.getVotacao().getId()).isEqualTo(idRequested);
		assertThat(votacaoResultadoResponseDTO.getFavoravel()).isEqualTo(0);
		assertThat(votacaoResultadoResponseDTO.getDesfavoravel()).isEqualTo(1);
    }
    
    @Transactional
    @Test
    void testGetResultadoByIdWithoutVotoDesfavoravel() throws Exception {
    	//Setup
    	Pauta pauta = new Pauta();
    	pauta.setDescricao("Pauta teste");
    	pauta = pautaRepository.save(pauta);
    	
    	Votacao votacao = new Votacao();
    	votacao.setPauta(pauta);
    	votacao.setPrazo(LocalDateTime.now().minusMinutes(5));
    	votacao = votacaoRepository.save(votacao);
    	
    	int idRequested = votacao.getId();

    	Voto voto = new Voto();
    	voto.setCpf("12345678910");
    	voto.setFavoravel(true);
    	voto.setTimestamp(LocalDateTime.now());
    	voto.setVotacao(votacao);
    	
    	voto= votoRepository.save(voto);
    	
    	//When
    	 MockHttpServletResponse response = mvc.perform(get("/votacoes/"+idRequested+"/resultado")).andReturn().getResponse();
    	
    	//Then
    	 VotacaoResultadoResponseDTO votacaoResultadoResponseDTO = objectMapper.readValue(response.getContentAsByteArray(), VotacaoResultadoResponseDTO.class);

    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(votacaoResultadoResponseDTO.getVotacao().getId()).isEqualTo(idRequested);
		assertThat(votacaoResultadoResponseDTO.getFavoravel()).isEqualTo(1);
		assertThat(votacaoResultadoResponseDTO.getDesfavoravel()).isEqualTo(0);
    }
    
    
    @Transactional
    @Test
    void testGetResultadoByIdWithoutVotos() throws Exception {
    	//Setup
    	Pauta pauta = new Pauta();
    	pauta.setDescricao("Pauta teste");
    	pauta = pautaRepository.save(pauta);
    	
    	Votacao votacao = new Votacao();
    	votacao.setPauta(pauta);
    	votacao.setPrazo(LocalDateTime.now().minusMinutes(5));
    	votacao = votacaoRepository.save(votacao);
    	
    	int idRequested = votacao.getId();
    	
    	//When
    	 MockHttpServletResponse response = mvc.perform(get("/votacoes/"+idRequested+"/resultado")).andReturn().getResponse();
    	
    	//Then
    	 VotacaoResultadoResponseDTO votacaoResultadoResponseDTO = objectMapper.readValue(response.getContentAsByteArray(), VotacaoResultadoResponseDTO.class);

    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(votacaoResultadoResponseDTO.getVotacao().getId()).isEqualTo(idRequested);
		assertThat(votacaoResultadoResponseDTO.getFavoravel()).isEqualTo(0);
		assertThat(votacaoResultadoResponseDTO.getDesfavoravel()).isEqualTo(0);
    }
    
    
    
    

}
