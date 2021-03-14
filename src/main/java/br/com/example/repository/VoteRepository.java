package br.com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.example.model.Vote;

/**
 * Interface que define o comportamento do gerenciador de dados da entidade voto
 * 
 * @author Danilo Alexandre
 *
 */
public interface VoteRepository extends JpaRepository<Vote, Integer> {

	List<Vote> getByVotingId(int idVoting);

	Vote findByCpfAndVotingId(String cpf, int idVoting);

	Long countByFavorableAndVotingId(boolean favorable, int idVoting);

}
