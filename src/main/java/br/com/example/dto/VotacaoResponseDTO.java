package br.com.example.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;


/**
 * Entidade que repesenta o conteúdo a ser enviado no body das respostas às requisições de votações.
 * 
 * @author Danilo Alexandre
 *
 */
@Getter
@Setter
public class VotacaoResponseDTO {

	@JsonProperty("id")
	private int id;

	@JsonProperty("prazo")
	private LocalDateTime prazo;
	
	@JsonProperty("pauta")
	private PautaResponseDTO pauta;

}
