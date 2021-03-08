package br.com.example.service;

import java.util.List;

import br.com.example.exception.ResourceNotFoundException;
import br.com.example.model.Pauta;
/**
 * Interface que define o comportamento do provedor de serviço relacionado à pauta
 * 
 * @author Danilo Alexandre
 *
 */
public interface PautaService {
	/**
	 * Retorna uma lista com todas as pautas cdasrtradas no sistema.
	 * @return Uma lista com as pautas
	 */
	public List<Pauta> getAll();
	
	/**
	 * Retorna uma pauta com base no identificador informado
	 * @param id o identificador da pauta
	 * @return A pauta
	 * @throws ResourceNotFoundException Caso não encontre pauta relacionada ao identificador informado.
	 */
	public Pauta get(int id)  throws ResourceNotFoundException;
	
	/**
	 * Armazena uma pauta no sistema
	 * @param pauta A pauta a ser armazenada.
	 * @return A pauta que foi armazenda carregada com seu identificador.
	 */
	public Pauta save(Pauta pauta);
	
	/**
	 * Remove uma pauta com base no identificador informado 
	 * @param id o identificador da pauta
	 * @throws ResourceNotFoundException Caso não encontre pauta relacionada ao identificador informado
	 */
	public void delete(int id) throws ResourceNotFoundException;

}
