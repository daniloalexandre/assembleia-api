package br.com.example.exception;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Exceção de integridade de dados
 * 
 * @author Danilo Alexandre
 *
 */
@Getter
@Setter
public class DataIntegrityException extends Exception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4554029685245592358L;

	private String message;
	private Object[] args;

	public DataIntegrityException(String message, Object... args) {
		super(message);
		this.message = message;
		this.args = args;
	}

}
