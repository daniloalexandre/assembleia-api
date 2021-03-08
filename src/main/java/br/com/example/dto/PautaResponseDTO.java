package br.com.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * Entidade que repesenta o conteúdo a ser enviado no body das respostas às requisições de pautas.
 * 
 * @author Danilo Alexandre
 *
 */
@Getter
@Setter
public class PautaResponseDTO {

	@JsonProperty("id")
	private int id;

	@JsonProperty("descricao")
	private String descricao;

}
