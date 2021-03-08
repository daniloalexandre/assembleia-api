package br.com.example.exception;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Exceção de impossibilidade de voto.
 * 
 * @author Danilo Alexandre
 *
 */
@Getter
@Setter
public class UnbaleVoteException extends Exception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5868012771822408114L;
	
	private String message;
	private Object[] args;

	public UnbaleVoteException(String message, Object ...args) {
		super(message);
		this.message = message;
		this.args = args;
	}

}
