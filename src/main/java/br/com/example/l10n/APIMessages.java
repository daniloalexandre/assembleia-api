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
	AFFILIATED_NOT_FOUND("api.message.affiliated.not_found"),
	AFFILIATED_UNABLE("api.message.affiliated.unable"),
	AGENDA_NOT_FOUND("api.message.agenda.not_found"),
	THIRD_PART_ERROR("api.message.third_part.error"),
	VOTING_NOT_FOUND("api.message.voting.not_found"),
	VOTE_ALREADY_COMPUTED("api.message.vote.already_computed"),
	VOTE_EXPIRED_PERIOD("api.message.vote.expired_period"),
	VOTE_NOT_FOUND("api.message.vote.not_found"),
	;
	
	private APIMessages(String code) {
		this.code = code;
	}
	
	private String code;
	
	

}
