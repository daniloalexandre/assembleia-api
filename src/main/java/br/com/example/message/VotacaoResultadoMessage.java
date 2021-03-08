package br.com.example.message;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
/**
 * Entidade que representa uma mensagem no controle de mensageria do sistema.
 * A entidade representa uma mensagem de resultado de votação.
 * 
 * @author Danilo Alexandre
 *
 */
@Getter
@Setter
public class VotacaoResultadoMessage {

	@JsonProperty("pauta_descricao")
	private String descricao;

	@JsonProperty("id_votacao")
	private int votacao;

	@JsonProperty("total_favoravel")
	private BigInteger favoravel;

	@JsonProperty("total_desfavoravel")
	private BigInteger desfavoravel;
	
	/**
	 * Cria uma mensagem de resultado de votação contendo os valores passados por parêmetro
	 * @param descricao A descrição da pauta
	 * @param votacao O identificador da votação
	 * @param favoravel O total de votos favoráveis
	 * @param desfavoravel O total de votos desfavoráveis
	 * @return A mensagem preeenchida com os valores informados.
	 */
	public static VotacaoResultadoMessage createVotacaoResultadoMessage(String descricao, int votacao, BigInteger favoravel, BigInteger desfavoravel) {
		VotacaoResultadoMessage topicMessage = new VotacaoResultadoMessage();
		topicMessage.setDescricao(descricao);
		topicMessage.setVotacao(votacao);
		topicMessage.setFavoravel(favoravel);
		topicMessage.setDesfavoravel(desfavoravel);
		return topicMessage;
	}

	@Override
	public String toString() {
		return String.format("Resultado [pauta : %s ; votacao: %s; favoravel: %s ; desfavoravel: %s]", descricao,
				votacao, favoravel, desfavoravel);
	}



	

}
