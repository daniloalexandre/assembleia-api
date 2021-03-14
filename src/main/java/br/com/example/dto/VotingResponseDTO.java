package br.com.example.dto;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * Entidade que repesenta o conteúdo a ser enviado no body das respostas às
 * requisições de votações.
 * 
 * @author Danilo Alexandre
 *
 */
@Getter
@Setter
public class VotingResponseDTO {

	@JsonProperty("id")
	private int id;

	@JsonProperty("deadline")
	private ZonedDateTime deadline;

	@JsonProperty("pauta")
	private AgendaResponseDTO agenda;

}
