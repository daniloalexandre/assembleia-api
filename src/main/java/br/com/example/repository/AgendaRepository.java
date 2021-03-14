package br.com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.example.model.Agenda;

/**
 * Interface que define o comportamento do gerenciador de dados da entidade
 * pauta
 * 
 * @author Danilo Alexandre
 *
 */
public interface AgendaRepository extends JpaRepository<Agenda, Integer> {

}
