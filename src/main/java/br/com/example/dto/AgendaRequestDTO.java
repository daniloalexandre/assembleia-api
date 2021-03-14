package br.com.example.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/**
 * Entidade que representa o conteúdo enviado no body das requisições
 * relacionadas às pautas.
 * 
 * @author Danilo Alexandre
 *
 */
@Getter
@Setter
public class AgendaRequestDTO {

	@NotBlank
	private String description;

}
