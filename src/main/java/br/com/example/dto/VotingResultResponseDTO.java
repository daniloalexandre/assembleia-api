package br.com.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * Entidade que repesenta o conteúdo a ser enviado no body das respostas às
 * requisições de resultado de votação.
 * 
 * @author Danilo Alexandre
 *
 */
@Getter
@Setter
public class VotingResultResponseDTO {

	@JsonProperty("voting")
	private VotingResponseDTO voting;

	@JsonProperty("favorable")
	private Long favorable;

	@JsonProperty("unfavorable")
	private Long unfavorable;

}
