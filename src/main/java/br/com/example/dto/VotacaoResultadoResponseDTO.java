package br.com.example.dto;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
/**
 * Entidade que repesenta o conteúdo a ser enviado no body das respostas às requisições de resultado de votação.
 * 
 * @author Danilo Alexandre
 *
 */
@Getter
@Setter
public class VotacaoResultadoResponseDTO {
	
	
	@JsonProperty("votacao")
	private VotacaoResponseDTO votacao;
	
	@JsonProperty("favoravel")
	private BigInteger favoravel;
	
	@JsonProperty("desfavoravel")
	private BigInteger desfavoravel;

}
