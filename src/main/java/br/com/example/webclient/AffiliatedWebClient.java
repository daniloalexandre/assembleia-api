package br.com.example.webclient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.example.exception.ResourceNotFoundException;
import br.com.example.l10n.APIMessages;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

/**
 * Componente responsável por executar a requisição que verifica a situação do
 * associado. URL do serviço externo a ser requisitado:
 * https://user-info.herokuapp.com/users/{cpf}
 * 
 * 
 * @author Danilo Alexandre
 *
 */
@Component
public class AffiliatedWebClient {

	private int timeout = 5000;
	private String baseURL = "https://user-info.herokuapp.com";

	private static WebClient webClient;

	public AffiliatedWebClient() {
		if (webClient == null)
			init();
	}

	private void init() {

		HttpClient httpClient = HttpClient.create().option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeout)
				.responseTimeout(Duration.ofMillis(timeout))
				.doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(timeout, TimeUnit.MILLISECONDS))
						.addHandlerLast(new WriteTimeoutHandler(timeout, TimeUnit.MILLISECONDS)));

		webClient = WebClient.builder().baseUrl(baseURL).clientConnector(new ReactorClientHttpConnector(httpClient))
				.build();

	}

	/**
	 * Recupera a situação do associado através do CPF
	 * 
	 * @param cpf A String que representa o número sem potuação do CPF do associado.
	 *            EX.: 12345678910
	 * @return A resposta da requisição.
	 */
	public AffiliatedResponse consultaAssociado(String cpf) {

		return webClient.get().uri("/users/" + cpf).retrieve()
				.onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus),
						clientResponse -> Mono
								.error(new ResourceNotFoundException(APIMessages.AFFILIATED_NOT_FOUND.getCode(), cpf)))
				.bodyToMono(AffiliatedResponse.class).block();
	}

}
