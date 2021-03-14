package br.com.example.message;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Serviço do tipo consumidor de mensagens, criado com a finalidade de verificar
 * o consumo das mensagens enviadas pelo Produtor.
 * 
 * @author Danilo Alexandre
 *
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class VotingTopicConsumer {

	@Value("${voting.result.topic}")
	private String votingResultTopic;

	/**
	 * Consumidor de mensagens relativos ao resultado da votação
	 * 
	 * @param payload
	 */
	@KafkaListener(topics = "${voting.result.topic}", groupId = "group_id")
	public void consumeVotingResult(ConsumerRecord<String, Object> payload) {
		log.info("Tópico: {}", votingResultTopic);
		log.info("key: {}", payload.key());
		log.info("Headers: {}", payload.headers());
		log.info("Partion: {}", payload.partition());
		log.info("Order: {}", payload.value());

	}

}
