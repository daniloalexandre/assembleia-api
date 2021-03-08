package br.com.example.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
/**
 * Entidade que representa o conteúdo enviado no body das requisições relacionadas às pautas.
 * 
 * @author Danilo Alexandre
 *
 */
@Getter
@Setter
public class PautaRequestDTO {

	@NotNull
	@NotBlank
	private String descricao;

}
