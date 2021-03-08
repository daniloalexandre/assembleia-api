package br.com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.example.model.Pauta;
/**
 * Interface que define o comportamento do gerenciador de dados da entidade pauta
 * 
 * @author Danilo Alexandre
 *
 */
public interface PautaRepository extends JpaRepository<Pauta, Integer> {

}
