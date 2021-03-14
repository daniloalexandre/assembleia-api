package br.com.example.exception;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Entidade que representa o conteúdo da mensagem de erro enviada no body da
 * reposta à requisição.
 * 
 * 
 * @author Danilo Alexandre
 *
 */
public class ErrorResponse {

	@JsonProperty("timestamp")
	private ZonedDateTime timestamp;

	@JsonProperty("errors")
	private List<String> errors;

	public ErrorResponse() {
		super();
		this.timestamp = ZonedDateTime.now();
		this.errors = new ArrayList<String>();
	}

	public void addError(String error) {
		this.errors.add(error);
	}

}
