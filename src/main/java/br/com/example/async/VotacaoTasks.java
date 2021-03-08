package br.com.example.async;

import java.math.BigInteger;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.example.exception.AccessDeniedException;
import br.com.example.message.VotacaoResultadoMessage;
import br.com.example.message.VotacaoTopicProducer;
import br.com.example.model.Votacao;
import br.com.example.service.VotoService;
import br.com.example.utils.TimeUitls;
import lombok.extern.slf4j.Slf4j;
/**
 * Serviço responsável pelo processamento assincrono de tarefas relacionadas às votações em pautas.
 *  
 * @author Danilo Alexandre
 *
 */
@Slf4j
@Service
public class VotacaoTasks {

	@Autowired
	private VotoService service;

	@Autowired
	private VotacaoTopicProducer producer;

	/**
	 * Tarefa responsável processar o resultado da votação após o prazo decorrido para a votação.
	 * @param votacao A votação que terá seu resultado processado
	 * @param minutes O tempo de espera necessário. referente ao prazo de votação. 
	 */
	@Async
	public void processarResultadoAfterMinutes(Votacao votacao, long minutes) {

		try {
			Thread.sleep(TimeUitls.minutesInMillis(minutes));

			Map<Boolean, BigInteger> resultado = service.countVotos(votacao);

			producer.send(VotacaoResultadoMessage.createVotacaoResultadoMessage(votacao.getPauta().getDescricao(), votacao.getId(),
					resultado.get(Boolean.TRUE) != null ? resultado.get(Boolean.TRUE) : new BigInteger("0"), resultado.get(Boolean.FALSE) !=null ? resultado.get(Boolean.FALSE) : new BigInteger("0")));
		} catch (InterruptedException e) {
			log.error(e.getMessage());
			
		} catch (AccessDeniedException e) {
			log.error(e.getMessage());
		} 

		log.info(String.format("Votação %s da pauta %s foi encerrada", votacao.getId(),
				votacao.getPauta().getDescricao()));

	}

}
