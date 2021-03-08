package br.com.example.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Entidade que representa o conteúdo da mensagem de erro enviada no body da reposta à requisição.
 * 
 * 
 * @author Danilo Alexandre
 *
 */
public class ErrorResponse {

	@JsonProperty("timestamp")
	private LocalDateTime timestamp;

	@JsonProperty("errors")
	private List<String> errors;

	public ErrorResponse() {
		super();
		this.timestamp = LocalDateTime.now();
		this.errors = new ArrayList<String>();
	}

	public void addError(String error) {
		this.errors.add(error);
	}

}
