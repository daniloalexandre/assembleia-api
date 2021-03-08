package br.com.example.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;


/**
 * Entidade que repesenta o conteúdo a ser enviado no body das respostas às requisições de votos.
 * 
 * @author Danilo Alexandre
 *
 */
@Getter
@Setter
public class VotoResponseDTO {

	@JsonProperty("id")
	private int id;
	
	@JsonProperty("favoravel")
	private boolean favoravel;
	
	@JsonProperty("cpf")
	private String cpf;

	@JsonProperty("timestamp")
	private LocalDateTime timestamp;
	
	@JsonProperty("votacao")
	private VotacaoResponseDTO votacao;

}
