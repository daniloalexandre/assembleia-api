package br.com.example.model;

import java.time.LocalDateTime;
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
@Table(name = "votacao")
@Getter
@Setter
public class Votacao {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_votacao")
	private int id;

	@Column(name = "prazo")
	private LocalDateTime prazo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_pauta")
	private Pauta pauta;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "votacao")
	private List<Voto> votos;
	
	
	public static Votacao createVotacao(Pauta pauta, long duracao) {
		Votacao votacao = new Votacao();
		votacao.setPauta(pauta);
		votacao.setPrazo(TimeUitls.addMinutesFromNow(duracao));
		return votacao;
	}

}
