package br.com.example.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * Classe que representa o modelo de dados de uma pauta de assembléia
 * 
 * @author Danilo Alexandre
 * 
 *
 */

@Entity
@Table(name = "agenda")
@Getter
@Setter
public class Agenda {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_agenda")
	private int id;

	@Lob
	@Column(name = "description", columnDefinition = "BLOB", nullable = false)
	private String description;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "agenda")
	private List<Voting> votings;

}
