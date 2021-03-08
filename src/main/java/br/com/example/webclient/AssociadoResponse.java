package br.com.example.webclient;

import lombok.Getter;
import lombok.Setter;
/**
 * Entidade que representa a estrutura de resposta da requisição externa que verifica a situação do associado.
 * 
 * @author Danilo Alexandre
 *
 */
@Getter
@Setter
public class AssociadoResponse {
	
	public static final String ABLE_TO_VOTE = "ABLE_TO_VOTE";
	public static final String UNABLE_TO_VOTE = "UNABLE_TO_VOTE";

	private String status;

}
