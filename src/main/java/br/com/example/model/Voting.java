package br.com.example.model;

import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.example.utils.TimeUitls;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * Classe que representa o modelo de dados de uma votação de assembléia
 * 
 * @author Danilo Alexandre
 * 
 *
 */

@Entity
@Table(name = "voting")
@Getter
@Setter
public class Voting {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_voting")
	private int id;

	@Column(name = "deadline")
	private ZonedDateTime deadline;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_agenda")
	private Agenda agenda;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "voting")
	private List<Vote> votes;

	public static Voting createVoting(Agenda agenda, long deadline) {
		Voting election = new Voting();
		election.setAgenda(agenda);
		election.setDeadline(TimeUitls.addMinutesFromNow(deadline));
		return election;
	}

}
