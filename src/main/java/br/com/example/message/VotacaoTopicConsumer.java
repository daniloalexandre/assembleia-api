package br.com.example.message;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Serviço do tipo consumidor de mensagens, criado com a finalidade de verificar o consumo das mensagens enviadas pelo Produtor.
 * 
 * @author Danilo Alexandre
 *
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class VotacaoTopicConsumer {

	@Value("${topic.votacao.resultado}")
	private String votacaoResultado;

	/**
	 * Consumidor de mensagens relativos ao resultado da votação
	 * @param payload
	 */
	@KafkaListener(topics = "${topic.votacao.resultado}", groupId = "group_id")
	public void consumeVotacaoResultado(ConsumerRecord<String, Object> payload) {
		log.info("Tópico: {}", votacaoResultado);
		log.info("key: {}", payload.key());
		log.info("Headers: {}", payload.headers());
		log.info("Partion: {}", payload.partition());
		log.info("Order: {}", payload.value());

	}

}
