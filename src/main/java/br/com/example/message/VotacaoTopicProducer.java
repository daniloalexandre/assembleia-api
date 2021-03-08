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
public class VotacaoTopicProducer {

    @Value("${topic.votacao.resultado}")
    private String votacaoResultado;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Envia mensgens destinadas ao tópico de resultados de votação
     * 
     * @param message A mensagem a ser enviada.
     */
    public void send(VotacaoResultadoMessage message){
        log.info("Topic: {} - Payload enviado: {}", votacaoResultado, message);
        kafkaTemplate.send(votacaoResultado, message);
    }

}
