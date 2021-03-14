package br.com.example.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.BDDMockito.given;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.example.exception.DataIntegrityException;
import br.com.example.exception.UnbaleVoteException;
import br.com.example.l10n.APIMessages;
import br.com.example.model.Agenda;
import br.com.example.model.Voting;
import br.com.example.model.Vote;
import br.com.example.repository.VoteRepository;
import br.com.example.service.impl.VoteServiceImpl;
import br.com.example.webclient.AffiliatedResponse;
import br.com.example.webclient.AffiliatedWebClient;

@ExtendWith(MockitoExtension.class)
public class VoteServiceTest {

	@Mock
	private VoteRepository repository;

	@Mock
	private AffiliatedWebClient webClient;

	@InjectMocks
	private VoteServiceImpl voteService;

	@Test
	void testeSaveVotoJaComputado() {
		// Setup

		int idAgenda = 1;
		int idVoting = 1;
		int idVote = 1;
		String descricao = "Agenda teste";
		String cpf = "12345678910";

		Agenda agenda = new Agenda();
		agenda.setId(idAgenda);
		agenda.setDescription(descricao);

		Voting voting = new Voting();
		voting.setId(idVoting);
		voting.setAgenda(agenda);
		voting.setDeadline(ZonedDateTime.now().plusMinutes(5));

		Vote vote = new Vote();
		vote.setCpf(cpf);
		vote.setFavorable(true);
		vote.setTimestamp(ZonedDateTime.now());
		vote.setVoting(voting);

		Vote voteDB = new Vote();
		voteDB.setId(idVote);
		voteDB.setCpf(cpf);
		voteDB.setFavorable(true);
		voteDB.setTimestamp(ZonedDateTime.now());
		voteDB.setVoting(voting);

		// Given
		given(repository.findByCpfAndVotingId(cpf, idVoting)).willReturn(voteDB);

		// When
		UnbaleVoteException result = catchThrowableOfType(() -> voteService.save(voting, vote),
				UnbaleVoteException.class);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.getMessage()).isEqualTo(APIMessages.VOTE_ALREADY_COMPUTED.getCode());

	}

	@Test
	void testeSaveVotingExpirada() {
		// Setup

		int idAgenda = 1;
		int idVoting = 1;
		int idVote = 1;
		String descricao = "Agenda teste";
		String cpf = "12345678910";

		Agenda agenda = new Agenda();
		agenda.setId(idAgenda);
		agenda.setDescription(descricao);

		Voting voting = new Voting();
		voting.setId(idVoting);
		voting.setAgenda(agenda);
		voting.setDeadline(ZonedDateTime.now().minusMinutes(5));

		Vote vote = new Vote();
		vote.setCpf(cpf);
		vote.setFavorable(true);
		vote.setTimestamp(ZonedDateTime.now());
		vote.setVoting(voting);

		Vote voteDB = new Vote();
		voteDB.setId(idVote);
		voteDB.setCpf(cpf);
		voteDB.setFavorable(true);
		voteDB.setTimestamp(ZonedDateTime.now());
		voteDB.setVoting(voting);

		// Given
		given(repository.findByCpfAndVotingId(cpf, idVoting)).willReturn(null);

		// When
		DataIntegrityException result = catchThrowableOfType(() -> voteService.save(voting, vote),
				DataIntegrityException.class);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.getMessage()).isEqualTo(APIMessages.VOTE_EXPIRED_PERIOD.getCode());

	}

	@Test
	void testeSaveAssociadoNaoAutorizado() {
		// Setup

		int idAgenda = 1;
		int idVoting = 1;
		int idVote = 1;
		String descricao = "Agenda teste";
		String cpf = "12345678910";

		Agenda agenda = new Agenda();
		agenda.setId(idAgenda);
		agenda.setDescription(descricao);

		Voting voting = new Voting();
		voting.setId(idVoting);
		voting.setAgenda(agenda);
		voting.setDeadline(ZonedDateTime.now().plusMinutes(5));

		Vote vote = new Vote();
		vote.setCpf(cpf);
		vote.setFavorable(true);
		vote.setTimestamp(ZonedDateTime.now());
		vote.setVoting(voting);

		Vote voteDB = new Vote();
		voteDB.setId(idVote);
		voteDB.setCpf(cpf);
		voteDB.setFavorable(true);
		voteDB.setTimestamp(ZonedDateTime.now());
		voteDB.setVoting(voting);

		AffiliatedResponse associadoResponse = new AffiliatedResponse();
		associadoResponse.setStatus(AffiliatedResponse.UNABLE_TO_VOTE);
		// Given
		given(repository.findByCpfAndVotingId(cpf, idVoting)).willReturn(null);
		given(webClient.consultaAssociado(cpf)).willReturn(associadoResponse);

		// When
		UnbaleVoteException result = catchThrowableOfType(() -> voteService.save(voting, vote),
				UnbaleVoteException.class);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.getMessage()).isEqualTo(APIMessages.AFFILIATED_UNABLE.getCode());
		assertThat(result.getArgs()).isNotEmpty();
		assertThat(result.getArgs()[0]).isEqualTo(vote.getCpf());

	}

}
