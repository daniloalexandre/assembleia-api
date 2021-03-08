package br.com.example.exception;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Exceção de impossibilidade de acesso a alguma solicitação.
 * 
 * @author Danilo Alexandre
 *
 */
@Getter
@Setter
public class AccessDeniedException extends Exception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1742227474336971759L;
	
	private String message;
	private Object[] args;

	public AccessDeniedException(String message, Object ...args) {
		super(message);
		this.message = message;
		this.args = args;
	}

}
