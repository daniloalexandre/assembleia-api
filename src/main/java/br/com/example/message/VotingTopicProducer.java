package br.com.example.message;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Serviço de mensageria. Produtor de topicos e mensagens relativas a votação.
 * 
 * @author Danilo Alexandre
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VotingTopicProducer {

	@Value("${voting.result.topic}")
	private String votingResultTopic;

	private final KafkaTemplate<String, Object> kafkaTemplate;

	/**
	 * Envia mensgens destinadas ao tópico de resultados de votação
	 * 
	 * @param message A mensagem a ser enviada.
	 */
	public void send(VotingResultMessage message) {
		log.info("Topic: {} - Payload enviado: {}", votingResultTopic, message);
		kafkaTemplate.send(votingResultTopic, message);
	}

}
