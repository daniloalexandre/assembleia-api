package br.com.example.dto;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * Entidade que repesenta o conteúdo a ser enviado no body das respostas às
 * requisições de votos.
 * 
 * @author Danilo Alexandre
 *
 */
@Getter
@Setter
public class VoteResponseDTO {

	@JsonProperty("id")
	private int id;

	@JsonProperty("favorable")
	private boolean favorable;

	@JsonProperty("cpf")
	private String cpf;

	@JsonProperty("timestamp")
	private ZonedDateTime timestamp;

	@JsonProperty("voting")
	private VotingResponseDTO voting;

}
