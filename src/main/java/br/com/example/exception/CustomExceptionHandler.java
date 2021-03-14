package br.com.example.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.example.l10n.MessageSourceSetup;
import lombok.extern.slf4j.Slf4j;

/**
 * Componente responsável por gerenciar exceções e emitir as respostas
 * pdronizadas de acordo com cada tipo de exceção.
 * 
 * 
 * @author Danilo Alexandre
 *
 */

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	MessageSourceSetup messageSourceSetup;

	/**
	 * Processa exceções de recursos não encontrados e reponde ao requisitante.
	 * 
	 * @param ex      A exceção de recurso não encontrado
	 * @param request A requisição
	 * @return Mensagem de erro padrão para exceções de recursos não encontrados.
	 */
	@ExceptionHandler(value = { ResourceNotFoundException.class })
	protected ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex,
			WebRequest request) {

		String message = messageSourceSetup.messageSource().getMessage(ex.getMessage(), ex.getArgs(), null);
		log.error(message);
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.addError(message);

		return new ResponseEntity<ErrorResponse>(errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);

	}

	/**
	 * Processa exceções de requisições que comprometam de integridade dos dados do
	 * sistema
	 * 
	 * @param ex      A exceção de integridade de dados
	 * @param request A requisição
	 * @return Mensagem de erro padrão para exceções de integridade de dados.
	 */
	@ExceptionHandler(value = { DataIntegrityException.class })
	protected ResponseEntity<ErrorResponse> handleDataIntegrityException(DataIntegrityException ex,
			WebRequest request) {

		String message = messageSourceSetup.messageSource().getMessage(ex.getMessage(), ex.getArgs(), null);
		log.error(message);
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.addError(message);

		return new ResponseEntity<ErrorResponse>(errorResponse, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);

	}

	/**
	 * Processa exceções de requisição relativas ao voto.
	 * 
	 * @param ex      A exceção relativa ao voto
	 * @param request A requisição
	 * @return Mensagem de erro padrão para exceções relativas ao voto.
	 */
	@ExceptionHandler(value = { UnbaleVoteException.class })
	protected ResponseEntity<ErrorResponse> handleUnbaleVoteException(UnbaleVoteException ex, WebRequest request) {

		String message = messageSourceSetup.messageSource().getMessage(ex.getMessage(), ex.getArgs(), null);
		log.error(message);
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.addError(message);

		return new ResponseEntity<ErrorResponse>(errorResponse, new HttpHeaders(), HttpStatus.FORBIDDEN);

	}

	/**
	 * Processa exceções de requisição de acesso não permitido à informações.
	 * 
	 * @param ex      A exceção relativa ao acesso não permitido
	 * @param request A requisição
	 * @return Mensagem de erro padrão para exceções relativas ao acesso.
	 */
	@ExceptionHandler(value = { ThirdPartException.class })
	protected ResponseEntity<ErrorResponse> handleThirdPartException(ThirdPartException ex, WebRequest request) {

		String message = messageSourceSetup.messageSource().getMessage(ex.getMessage(), ex.getArgs(), null);
		log.error(message);
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.addError(message);

		return new ResponseEntity<ErrorResponse>(errorResponse, new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY);

	}

	/**
	 * Sobscreve o handler que processa as exceções que ocorrem durante a validação
	 * de DTOs.
	 * 
	 * @see ResponseEntityExceptionHandler.handleMethodArgumentNotValid
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		log.error(ex.getMessage());

		ErrorResponse errorResponse = new ErrorResponse();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errorResponse.addError(fieldName + ": " + errorMessage);
		});

		return new ResponseEntity<Object>(errorResponse, headers, status);

	}

}
