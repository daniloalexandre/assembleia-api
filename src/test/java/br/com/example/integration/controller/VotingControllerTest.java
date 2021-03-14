package br.com.example.integration.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.ZonedDateTime;

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

import br.com.example.dto.VotingRequestDTO;
import br.com.example.dto.VotingResponseDTO;
import br.com.example.dto.VotingResultResponseDTO;
import br.com.example.dto.VoteResponseDTO;
import br.com.example.exception.ErrorResponse;
import br.com.example.model.Agenda;
import br.com.example.model.Voting;
import br.com.example.model.Vote;
import br.com.example.repository.AgendaRepository;
import br.com.example.repository.VotingRepository;
import br.com.example.repository.VoteRepository;

@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
public class VotingControllerTest {
	
	@Autowired
    private WebApplicationContext context;
	
	@Autowired
	private AgendaRepository agendaRepository;
	
	@Autowired
	private VotingRepository votingRepository;
	
	@Autowired
	private VoteRepository voteRepository;
	
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
    	Agenda agenda = new Agenda();
    	agenda.setDescription("Agenda teste");
    	agenda = agendaRepository.save(agenda);
    	
    	Voting voting = Voting.createVoting(agenda, 5);
    	voting = votingRepository.save(voting);
    	
    	//When
    	 MockHttpServletResponse response = mvc.perform(get("/votings")).andReturn().getResponse();
    	
    	//Then
    	 VotingResponseDTO[] responseObjects = objectMapper.readValue(response.getContentAsByteArray(), VotingResponseDTO[].class);

    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(responseObjects).isNotEmpty();
		
    }
    
    
    @Transactional
    @Test
    void testGetById() throws Exception {
    	//Setup
    	String description = "Agenda teste";
    	
    	Agenda agenda = new Agenda();
    	agenda.setDescription(description);
    	agenda = agendaRepository.save(agenda);
    	
    	Voting voting = Voting.createVoting(agenda, 5);
    	voting = votingRepository.save(voting);
    	
    	int idRequested = voting.getId();
    	
    	//When
    	 MockHttpServletResponse response = mvc.perform(get("/votings/"+idRequested)).andReturn().getResponse();
    	
    	//Then
    	 VotingResponseDTO votingResponseDTO = objectMapper.readValue(response.getContentAsByteArray(), VotingResponseDTO.class);

    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    	assertThat(votingResponseDTO).isNotNull();
		assertThat(votingResponseDTO.getId()).isEqualTo(idRequested);
		
    }
    
    @Transactional
    @Test
    void testGetByIdResourceNotFound() throws Exception {
    	//Setup
    	int idRequested = 100;
    	
    	//When
    	MockHttpServletResponse response = mvc.perform(get("/votings/"+idRequested)).andReturn().getResponse();
    	
    	//Then
    	ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsByteArray(), ErrorResponse.class);

    	assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    	assertThat(errorResponse).isNotNull();
		
    }
    
    
    @Transactional
    @Test
    void testSave() throws Exception {
    	//Setup 
    	String description = "Agenda teste";
    	
    	Agenda agenda = new Agenda();
    	agenda.setDescription(description);
    	agenda = agendaRepository.save(agenda);
    	
    	int idAgenda = agenda.getId();
    	VotingRequestDTO requestDTO = new VotingRequestDTO();
    	requestDTO.setDuration(5);
    	requestDTO.setIdAgenda(idAgenda);
    	
    	
    	//When
    	 MockHttpServletResponse response = mvc.perform(post("/votings").contentType(MediaType.APPLICATION_JSON)
     			.content(objectMapper.writeValueAsString(requestDTO))).andReturn().getResponse();
    	
    	//Then
    	 VotingResponseDTO votingResponseDTO = objectMapper.readValue(response.getContentAsByteArray(), VotingResponseDTO.class);

    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    	assertThat(votingResponseDTO).isNotNull();
		assertThat(votingResponseDTO.getId()).isPositive();
		assertThat(votingResponseDTO.getAgenda().getId()).isEqualTo(idAgenda);
		
    }
    
    
    @Transactional
    @Test
    void testSaveMethodArgumentNotValid() throws Exception {
    	//Setup
    	VotingRequestDTO requestDTO = new VotingRequestDTO();
    	requestDTO.setIdAgenda(0);
    	
    	//When
    	 MockHttpServletResponse response = mvc.perform(post("/votings").contentType(MediaType.APPLICATION_JSON)
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
    	String description = "Agenda teste";
    	
    	Agenda agenda = new Agenda();
    	agenda.setDescription(description);
    	agenda = agendaRepository.save(agenda);
    	
    	Voting voting = Voting.createVoting(agenda, 5);
    	voting = votingRepository.save(voting);
    	
    	int idRequested = voting.getId();
    	
    	//When
    	 MockHttpServletResponse response = mvc.perform(delete("/votings/"+idRequested)).andReturn().getResponse();
    	
    	//Then
    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    	
    	
    	//When
    	response = mvc.perform(get("/votings/"+idRequested)).andReturn().getResponse();
    	
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
    	MockHttpServletResponse response = mvc.perform(delete("/votings/"+idRequested)).andReturn().getResponse();
    	
    	//Then
    	ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsByteArray(), ErrorResponse.class);

    	assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    	assertThat(errorResponse).isNotNull();
		
    }
    
    
    @Transactional
    @Test
    void testGetVotosById() throws Exception {
    	//Setup
    	Agenda agenda = new Agenda();
    	agenda.setDescription("Agenda teste");
    	agenda = agendaRepository.save(agenda);
    	
    	Voting voting = Voting.createVoting(agenda, 5);
    	voting = votingRepository.save(voting);
    	
    	int idRequested = voting.getId();
    	
    	Vote vote = new Vote();
    	vote.setCpf("12345678910");
    	vote.setFavorable(true);
    	vote.setTimestamp(ZonedDateTime.now());
    	vote.setVoting(voting);
    	
    	vote = voteRepository.save(vote);
    	
    	Vote vote2 = new Vote();
    	vote2.setCpf("12345678910");
    	vote2.setFavorable(false);
    	vote2.setTimestamp(ZonedDateTime.now());
    	vote2.setVoting(voting);
    	
    	vote2 = voteRepository.save(vote2);
    	
    	//When
    	 MockHttpServletResponse response = mvc.perform(get("/votings/"+idRequested+"/votes")).andReturn().getResponse();
    	
    	//Then
    	 VoteResponseDTO[] responseObjects = objectMapper.readValue(response.getContentAsByteArray(), VoteResponseDTO[].class);

    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(responseObjects).isNotEmpty();
		
    }
    
    @Transactional
    @Test
    void testGetResultadoById() throws Exception {
    	//Setup
    	Agenda agenda = new Agenda();
    	agenda.setDescription("Agenda teste");
    	agenda = agendaRepository.save(agenda);
    	
    	Voting voting = new Voting();
    	voting.setAgenda(agenda);
    	voting.setDeadline(ZonedDateTime.now().minusMinutes(5));
    	voting = votingRepository.save(voting);
    	
    	int idRequested = voting.getId();
    	
    	Vote vote = new Vote();
    	vote.setCpf("12345678910");
    	vote.setFavorable(true);
    	vote.setTimestamp(ZonedDateTime.now());
    	vote.setVoting(voting);
    	
    	vote = voteRepository.save(vote);
    	
    	Vote vote2 = new Vote();
    	vote2.setCpf("12345678910");
    	vote2.setFavorable(false);
    	vote2.setTimestamp(ZonedDateTime.now());
    	vote2.setVoting(voting);
    	
    	vote2 = voteRepository.save(vote2);
    	
    	//When
    	 MockHttpServletResponse response = mvc.perform(get("/votings/"+idRequested+"/result")).andReturn().getResponse();
    	
    	//Then
    	VotingResultResponseDTO votingResultadoResponseDTO = objectMapper.readValue(response.getContentAsByteArray(), VotingResultResponseDTO.class);

    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(votingResultadoResponseDTO.getVoting().getId()).isEqualTo(idRequested);
		assertThat(votingResultadoResponseDTO.getFavorable()).isEqualTo(1);
		assertThat(votingResultadoResponseDTO.getUnfavorable()).isEqualTo(1);
    }
    
    @Transactional
    @Test
    void testGetResultadoByIdVotingNaoFinalizada() throws Exception {
    	//Setup
    	Agenda agenda = new Agenda();
    	agenda.setDescription("Agenda teste");
    	agenda = agendaRepository.save(agenda);
    	
    	Voting voting = Voting.createVoting(agenda, 5);
    	voting = votingRepository.save(voting);
    	
    	int idRequested = voting.getId();
    	
    	Vote vote = new Vote();
    	vote.setCpf("12345678910");
    	vote.setFavorable(true);
    	vote.setTimestamp(ZonedDateTime.now());
    	vote.setVoting(voting);
    	
    	vote = voteRepository.save(vote);
    	
    	Vote vote2 = new Vote();
    	vote2.setCpf("12345678910");
    	vote2.setFavorable(false);
    	vote2.setTimestamp(ZonedDateTime.now());
    	vote2.setVoting(voting);
    	
    	vote2 = voteRepository.save(vote2);
    	
    	//When
    	 MockHttpServletResponse response = mvc.perform(get("/votings/"+idRequested+"/result")).andReturn().getResponse();
    	
    	//Then
    	VotingResultResponseDTO votingResultadoResponseDTO = objectMapper.readValue(response.getContentAsByteArray(), VotingResultResponseDTO.class);

     	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(votingResultadoResponseDTO.getVoting().getId()).isEqualTo(idRequested);
 		assertThat(votingResultadoResponseDTO.getFavorable()).isEqualTo(1);
 		assertThat(votingResultadoResponseDTO.getUnfavorable()).isEqualTo(1);
    }
    
    @Transactional
    @Test
    void testGetResultadoByIdWithoutVotoFavorable() throws Exception {
    	//Setup
    	Agenda agenda = new Agenda();
    	agenda.setDescription("Agenda teste");
    	agenda = agendaRepository.save(agenda);
    	
    	Voting voting = new Voting();
    	voting.setAgenda(agenda);
    	voting.setDeadline(ZonedDateTime.now().minusMinutes(5));
    	voting = votingRepository.save(voting);
    	
    	int idRequested = voting.getId();

    	Vote vote = new Vote();
    	vote.setCpf("12345678910");
    	vote.setFavorable(false);
    	vote.setTimestamp(ZonedDateTime.now());
    	vote.setVoting(voting);
    	
    	vote= voteRepository.save(vote);
    	
    	//When
    	 MockHttpServletResponse response = mvc.perform(get("/votings/"+idRequested+"/result")).andReturn().getResponse();
    	
    	//Then
    	 VotingResultResponseDTO votingResultadoResponseDTO = objectMapper.readValue(response.getContentAsByteArray(), VotingResultResponseDTO.class);

    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(votingResultadoResponseDTO.getVoting().getId()).isEqualTo(idRequested);
		assertThat(votingResultadoResponseDTO.getFavorable()).isEqualTo(0);
		assertThat(votingResultadoResponseDTO.getUnfavorable()).isEqualTo(1);
    }
    
    @Transactional
    @Test
    void testGetResultadoByIdWithoutVotoUnfavorable() throws Exception {
    	//Setup
    	Agenda agenda = new Agenda();
    	agenda.setDescription("Agenda teste");
    	agenda = agendaRepository.save(agenda);
    	
    	Voting voting = new Voting();
    	voting.setAgenda(agenda);
    	voting.setDeadline(ZonedDateTime.now().minusMinutes(5));
    	voting = votingRepository.save(voting);
    	
    	int idRequested = voting.getId();

    	Vote vote = new Vote();
    	vote.setCpf("12345678910");
    	vote.setFavorable(true);
    	vote.setTimestamp(ZonedDateTime.now());
    	vote.setVoting(voting);
    	
    	vote= voteRepository.save(vote);
    	
    	//When
    	 MockHttpServletResponse response = mvc.perform(get("/votings/"+idRequested+"/result")).andReturn().getResponse();
    	
    	//Then
    	 VotingResultResponseDTO votingResultadoResponseDTO = objectMapper.readValue(response.getContentAsByteArray(), VotingResultResponseDTO.class);

    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(votingResultadoResponseDTO.getVoting().getId()).isEqualTo(idRequested);
		assertThat(votingResultadoResponseDTO.getFavorable()).isEqualTo(1);
		assertThat(votingResultadoResponseDTO.getUnfavorable()).isEqualTo(0);
    }
    
    
    @Transactional
    @Test
    void testGetResultadoByIdWithoutVotos() throws Exception {
    	//Setup
    	Agenda agenda = new Agenda();
    	agenda.setDescription("Agenda teste");
    	agenda = agendaRepository.save(agenda);
    	
    	Voting voting = new Voting();
    	voting.setAgenda(agenda);
    	voting.setDeadline(ZonedDateTime.now().minusMinutes(5));
    	voting = votingRepository.save(voting);
    	
    	int idRequested = voting.getId();
    	
    	//When
    	 MockHttpServletResponse response = mvc.perform(get("/votings/"+idRequested+"/result")).andReturn().getResponse();
    	
    	//Then
    	 VotingResultResponseDTO votingResultadoResponseDTO = objectMapper.readValue(response.getContentAsByteArray(), VotingResultResponseDTO.class);

    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(votingResultadoResponseDTO.getVoting().getId()).isEqualTo(idRequested);
		assertThat(votingResultadoResponseDTO.getFavorable()).isEqualTo(0);
		assertThat(votingResultadoResponseDTO.getUnfavorable()).isEqualTo(0);
    }
    
    
    
    

}
