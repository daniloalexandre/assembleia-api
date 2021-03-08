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
@Table(name = "pauta")
@Getter
@Setter
public class Pauta {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_pauta")
	private int id;

	@Lob
	@Column(name = "descricao", columnDefinition = "BLOB", nullable = false)
	private String descricao;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pauta")
	private List<Votacao> votacoes;

}
