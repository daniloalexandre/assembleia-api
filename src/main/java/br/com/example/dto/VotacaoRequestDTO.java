package br.com.example.dto;

import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * Entidade que representa o conteúdo enviado no body das requisições relacionadas às votações.
 * 
 * @author Danilo Alexandre
 *
 */
@Getter
@Setter
public class VotacaoRequestDTO {
	
	@JsonProperty("duracao_minutos")
	private long duracao;

	
	@Min(1)
	@JsonProperty("id_pauta")
	private int idPauta;

	

}
