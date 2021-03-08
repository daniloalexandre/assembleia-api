package br.com.example.l10n;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
/**
 * Componente de configuração do controle de localização de mensagens do sistema.
 * 
 * 
 * @author Danilo Alexandre
 *
 */
@Configuration
public class MessageSetup extends MessageSourceSetupAbstract {

	@Value("${application.l10n.basenames}")
	String[] basenames;
	
	@Override
	@Bean
	public ResourceBundleMessageSource messageSource() {
		Locale.setDefault(new Locale("pt", "BR"));
		return super.messageSource();
	}
	
	@Override
	public String[] getBasenames() {
		return basenames;
	}
	
}
