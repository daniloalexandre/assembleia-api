package br.com.example.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * Entidade que representa uma mensagem no controle de mensageria do sistema. A
 * entidade representa uma mensagem de resultado de votação.
 * 
 * @author Danilo Alexandre
 *
 */
@Getter
@Setter
public class VotingResultMessage {

	@JsonProperty("agenda_description")
	private String description;

	@JsonProperty("id_voting")
	private int voting;

	@JsonProperty("favorable_total")
	private Long favorable;

	@JsonProperty("unfavorable_total")
	private Long unfavorable;

	/**
	 * Cria uma mensagem de resultado de votação contendo os valores passados por
	 * parêmetro
	 * 
	 * @param descricao    A descrição da pauta
	 * @param votacao      O identificador da votação
	 * @param favoravel    O total de votos favoráveis
	 * @param desfavoravel O total de votos desfavoráveis
	 * @return A mensagem preeenchida com os valores informados.
	 */
	public static VotingResultMessage createVotacaoResultadoMessage(String description, int voting, Long favorable,
			Long unfavorable) {
		VotingResultMessage VotingResultMessage = new VotingResultMessage();
		VotingResultMessage.setDescription(description);
		VotingResultMessage.setVoting(voting);
		VotingResultMessage.setFavorable(favorable);
		VotingResultMessage.setUnfavorable(unfavorable);
		return VotingResultMessage;
	}

	@Override
	public String toString() {
		return String.format("Resultado [pauta : %s ; votacao: %s; favoravel: %s ; desfavoravel: %s]", description,
				voting, favorable, unfavorable);
	}

}
