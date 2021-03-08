package br.com.example.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.example.exception.DataIntegrityException;
import br.com.example.exception.UnbaleVoteException;
import br.com.example.l10n.APIMessages;
import br.com.example.model.Pauta;
import br.com.example.model.Votacao;
import br.com.example.model.Voto;
import br.com.example.repository.VotoRepository;
import br.com.example.service.impl.VotoServiceImpl;
import br.com.example.webclient.AssociadoResponse;
import br.com.example.webclient.AssociadoWebClient;

@ExtendWith(MockitoExtension.class)
public class VotoServiceTest {
	
	@Mock
	private VotoRepository repository;
	
	@Mock
	private AssociadoWebClient webClient;
	
	@InjectMocks
	private VotoServiceImpl votoService;
	
	
	@Test
	void testeSaveVotoJaComputado() {
		//Setup
		
		int idPauta = 1;
		int idVotacao = 1;
		int idVoto = 1;
		String descricao = "Pauta teste";
		String cpf = "12345678910";
		
    	Pauta pauta = new Pauta();
    	pauta.setId(idPauta);
    	pauta.setDescricao(descricao);
    	
    	
    	Votacao votacao = new Votacao();
    	votacao.setId(idVotacao);
    	votacao.setPauta(pauta);
    	votacao.setPrazo(LocalDateTime.now().plusMinutes(5));
    	
    	Voto voto = new Voto();
    	voto.setCpf(cpf);
    	voto.setFavoravel(true);
    	voto.setTimestamp(LocalDateTime.now());
    	voto.setVotacao(votacao);
    	
    	Voto votoDB = new Voto();
    	votoDB.setId(idVoto);
    	votoDB.setCpf(cpf);
    	votoDB.setFavoravel(true);
    	votoDB.setTimestamp(LocalDateTime.now());
    	votoDB.setVotacao(votacao);

		//Given
		given(repository.findByCPFAndVotacaoId(cpf, idVotacao)).willReturn(votoDB);
		
		
		//When
		UnbaleVoteException result = catchThrowableOfType(() -> votoService.save(voto), UnbaleVoteException.class);
		
		//Then
		assertThat(result).isNotNull();
		assertThat(result.getMessage()).isEqualTo(APIMessages.VOTO_ALREADY_COMPUTED.getCode());
		
	}
	
	@Test
	void testeSaveVotacaoExpirada() {
		//Setup
		
		int idPauta = 1;
		int idVotacao = 1;
		int idVoto = 1;
		String descricao = "Pauta teste";
		String cpf = "12345678910";
		
    	Pauta pauta = new Pauta();
    	pauta.setId(idPauta);
    	pauta.setDescricao(descricao);
    	
    	
    	Votacao votacao = new Votacao();
    	votacao.setId(idVotacao);
    	votacao.setPauta(pauta);
    	votacao.setPrazo(LocalDateTime.now().minusMinutes(5));
    	
    	Voto voto = new Voto();
    	voto.setCpf(cpf);
    	voto.setFavoravel(true);
    	voto.setTimestamp(LocalDateTime.now());
    	voto.setVotacao(votacao);
    	
    	Voto votoDB = new Voto();
    	votoDB.setId(idVoto);
    	votoDB.setCpf(cpf);
    	votoDB.setFavoravel(true);
    	votoDB.setTimestamp(LocalDateTime.now());
    	votoDB.setVotacao(votacao);
		
		//Given
		given(repository.findByCPFAndVotacaoId(cpf, idVotacao)).willReturn(null);
		
		
		//When
		DataIntegrityException result = catchThrowableOfType(() -> votoService.save(voto), DataIntegrityException.class);
		
		//Then
		assertThat(result).isNotNull();
		assertThat(result.getMessage()).isEqualTo(APIMessages.VOTO_EXPIRED_PERIOD.getCode());
		
	}
	
	@Test
	void testeSaveAssociadoNaoAutorizado() {
		//Setup
		
		int idPauta = 1;
		int idVotacao = 1;
		int idVoto = 1;
		String descricao = "Pauta teste";
		String cpf = "12345678910";
		
    	Pauta pauta = new Pauta();
    	pauta.setId(idPauta);
    	pauta.setDescricao(descricao);
    	
    	
    	Votacao votacao = new Votacao();
    	votacao.setId(idVotacao);
    	votacao.setPauta(pauta);
    	votacao.setPrazo(LocalDateTime.now().plusMinutes(5));
    	
    	Voto voto = new Voto();
    	voto.setCpf(cpf);
    	voto.setFavoravel(true);
    	voto.setTimestamp(LocalDateTime.now());
    	voto.setVotacao(votacao);
    	
    	Voto votoDB = new Voto();
    	votoDB.setId(idVoto);
    	votoDB.setCpf(cpf);
    	votoDB.setFavoravel(true);
    	votoDB.setTimestamp(LocalDateTime.now());
    	votoDB.setVotacao(votacao);
		
    	AssociadoResponse associadoResponse = new AssociadoResponse();
    	associadoResponse.setStatus(AssociadoResponse.UNABLE_TO_VOTE);
		//Given
		given(repository.findByCPFAndVotacaoId(cpf, idVotacao)).willReturn(null);
		given(webClient.consultaAssociado(cpf)).willReturn(associadoResponse);
		
		
		//When
		UnbaleVoteException result = catchThrowableOfType(() -> votoService.save(voto), UnbaleVoteException.class);
		
		//Then
		assertThat(result).isNotNull();
		assertThat(result.getMessage()).isEqualTo(APIMessages.ASSOCIADO_UNABLE.getCode());
		assertThat(result.getArgs()).isNotEmpty();
		assertThat(result.getArgs()[0]).isEqualTo(voto.getCpf());
		
	}

}
