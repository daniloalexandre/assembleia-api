package br.com.example.service;

import java.util.List;

import br.com.example.exception.ResourceNotFoundException;
import br.com.example.model.Agenda;
import br.com.example.model.Voting;

/**
 * Interface que define o comportamento do provedor de serviço relacionado à
 * votação
 * 
 * @author Danilo Alexandre
 *
 */
public interface VotingService {

	/**
	 * Retorna uma lista com todas as votações cdasrtradas no sistema.
	 * 
	 * @return Uma lista com as votações
	 */
	public List<Voting> getAll();

	/**
	 * Retorna uma votação com base no identificador informado
	 * 
	 * @param id o identificador da votação
	 * @return A votação
	 * @throws ResourceNotFoundException Caso não encontre votação relacionada ao
	 *                                   identificador informado.
	 */
	public Voting get(int id) throws ResourceNotFoundException;

	/**
	 * Armazena uma votação no sistema
	 * 
	 * @param votação A votação a ser armazenada.
	 * @return A votação que foi armazenda carregada com seu identificador.
	 */
	public Voting save(Agenda agenda, long duration);

	/**
	 * Remove uma votação com base no identificador informado
	 * 
	 * @param id o identificador da votação
	 * @throws ResourceNotFoundException Caso não encontre votação relacionada ao
	 *                                   identificador informado
	 */
	public void delete(int id) throws ResourceNotFoundException;

}
