package br.com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.example.model.Voting;

/**
 * Interface que define o comportamento do gerenciador de dados da entidade
 * votação
 * 
 * @author Danilo Alexandre
 *
 */
public interface VotingRepository extends JpaRepository<Voting, Integer> {

}
