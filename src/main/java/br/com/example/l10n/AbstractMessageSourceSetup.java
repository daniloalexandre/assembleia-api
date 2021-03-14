package br.com.example.l10n;

import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Classe abstrata que implementa o comportamento do controlador de localização
 * de mensagens do sistema.
 * 
 * 
 * @author Danilo Alexandre
 *
 */
public abstract class AbstractMessageSourceSetup implements MessageSourceSetup {

	ResourceBundleMessageSource source = new ResourceBundleMessageSource();

	@Override
	public ResourceBundleMessageSource messageSource() {

		source.setBasenames(getBasenames());
		source.setUseCodeAsDefaultMessage(true);
		return source;
	}

	public abstract String[] getBasenames();

}
