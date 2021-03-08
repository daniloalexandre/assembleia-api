package br.com.example.exception;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Exceção de impossibilidade de cosumir serviço de terceiro.
 * 
 * @author Danilo Alexandre
 *
 */
@Getter
@Setter
public class ThirdPartException extends Exception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6315442866689556075L;
	
	private String message;
	private Object[] args;

	public ThirdPartException(String message, Object ...args) {
		super(message);
		this.message = message;
		this.args = args;
	}

}
