package br.com.example.l10n;

import lombok.Getter;

/**
 * Enumaration de todos os códigos de mensagem utilizadas pelo sistema.
 * 
 * @author Danilo Alexandre
 *
 */
@Getter
public enum APIMessages implements Message {
	ASSOCIADO_NOT_FOUND("api.message.associado.not_found"),
	ASSOCIADO_UNABLE("api.message.associado.unable"),
	PAUTA_NOT_FOUND("api.message.pauta.not_found"),
	VOTACAO_NOT_EXPIRED("api.message.votacao.not_expired"),
	VOTACAO_NOT_FOUND("api.message.votacao.not_found"),
	VOTO_ALREADY_COMPUTED("api.message.voto.already_computed"),
	VOTO_EXPIRED_PERIOD("api.message.voto.expired_period"),
	VOTO_NOT_FOUND("api.message.voto.not_found"),
	;
	
	APIMessages(String code) {
		this.code = code;
	}
	private String code;
	
	

}
