package br.com.example.model;

import java.time.LocalDateTime;

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
@Table(name = "voto")
@Getter
@Setter
public class Voto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_voto")
	private int id;

	@Column(name = "favoravel")
	private boolean favoravel = Boolean.FALSE;
	
	@Column(name = "timestamp")
	private LocalDateTime timestamp;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_votacao")
	private Votacao votacao;

	@Column(name = "cpf")
	private String cpf;

}
