package br.com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.example.model.Votacao;

/**
 * Interface que define o comportamento do gerenciador de dados da entidade votação
 * 
 * @author Danilo Alexandre
 *
 */
public interface VotacaoRepository extends JpaRepository<Votacao, Integer> {

}
