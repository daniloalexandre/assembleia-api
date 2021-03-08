package br.com.example.exception;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Exceção de recurso não encontrado.
 * 
 * @author Danilo Alexandre
 *
 */
@Getter @Setter
public class ResourceNotFoundException extends Exception implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6862991736711367922L;
	
	private String message;
	private Object[] args;
	
	
	public ResourceNotFoundException(String message, Object ...args) {
		super(message);
		this.message = message;
		this.args = args;
	}

}
