package br.com.example.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.example.message.VotingResultMessage;
import br.com.example.message.VotingTopicProducer;
import br.com.example.model.Voting;
import br.com.example.service.VoteService;
import br.com.example.utils.TimeUitls;
import lombok.extern.slf4j.Slf4j;

/**
 * Serviço responsável pelo processamento assincrono de tarefas relacionadas às
 * votações em pautas.
 * 
 * @author Danilo Alexandre
 *
 */
@Slf4j
@Service
public class VotingTasks {

	@Autowired
	private VoteService service;

	@Autowired
	private VotingTopicProducer producer;

	/**
	 * Tarefa responsável processar o resultado da votação após o prazo decorrido
	 * para a votação.
	 * 
	 * @param voting  A votação que terá seu resultado processado
	 * @param minutes O tempo de espera necessário. referente ao prazo de votação.
	 */
	@Async
	public void processResultAfterMinutes(Voting voting, long minutes) {

		try {
			Thread.sleep(TimeUitls.minutesInMillis(minutes));

			producer.send(VotingResultMessage.createVotacaoResultadoMessage(voting.getAgenda().getDescription(),
					voting.getId(), service.countByFavorableAndVotingId(true, voting.getId()),
					service.countByFavorableAndVotingId(false, voting.getId())));
		} catch (InterruptedException e) {
			log.error(e.getMessage());

		}

		log.info(String.format("Votação %s da pauta %s foi encerrada", voting.getId(),
				voting.getAgenda().getDescription()));

	}

}
