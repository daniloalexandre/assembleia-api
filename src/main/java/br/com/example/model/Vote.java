package br.com.example.model;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * Classe que representa o modelo de dados de um voto
 * 
 * @author Danilo Alexandre
 * 
 *
 */
@Entity
@Table(name = "vote")
@Getter
@Setter
public class Vote {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_vote")
	private int id;

	@Column(name = "favorable")
	private boolean favorable = Boolean.FALSE;

	@Column(name = "timestamp")
	private ZonedDateTime timestamp;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_voting")
	private Voting voting;

	@Column(name = "cpf")
	private String cpf;

}
