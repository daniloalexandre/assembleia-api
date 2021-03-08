package br.com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.example.model.Voto;

/**
 * Interface que define o comportamento do gerenciador de dados da entidade voto
 * 
 * @author Danilo Alexandre
 *
 */
public interface VotoRepository extends JpaRepository<Voto, Integer>, VotoRepositoryCustom {
	
	List<Voto> getByVotacaoId(int idVotacao);
	
	@Query("SELECT voto FROM Voto voto WHERE voto.cpf = :cpf AND voto.votacao.id = :idVotacao")
	Voto findByCPFAndVotacaoId(@Param("cpf") String cpf, @Param("idVotacao") int idVotacao);

}
