package br.com.example.l10n;

import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Interface que representa o comportamento do controlador de localização de
 * mensagens do sistema.
 * 
 * @author Danilo Alexandre
 *
 */
public interface MessageSourceSetup {

	/**
	 * Retorna o controlador de localização que gerencia as mensagens do sistema.
	 * 
	 * @return O controlador de localização
	 */
	public ResourceBundleMessageSource messageSource();

}
