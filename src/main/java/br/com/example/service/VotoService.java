package br.com.example.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import br.com.example.exception.AccessDeniedException;
import br.com.example.exception.DataIntegrityException;
import br.com.example.exception.ResourceNotFoundException;
import br.com.example.exception.ThirdPartException;
import br.com.example.exception.UnbaleVoteException;
import br.com.example.model.Votacao;
import br.com.example.model.Voto;

/**
 * interface que define o comportamento do provedor de serviço relacionado ao voto
 * 
 * @author Danilo Alexandre
 *
 */
public interface VotoService {
	
	/**
	 * Retorna uma lista com todas os votos cdasrtrados no sistema.
	 * @return Uma lista com os votos
	 */
	public List<Voto> getAll();
	
	/**
	 * Retorna um voto com base no identificador informado
	 * @param id o identificador do voto
	 * @return O voto
	 * @throws ResourceNotFoundException Caso não encontre voto relacionada ao identificador informado.
	 */
	public Voto get(int id) throws ResourceNotFoundException;
	
	/**
	 * Armazena um voto no sistema
	 * @param voto O voto a ser armazenado
	 * @return O voto que foi armazenda carregada com seu identificador.
	 * @throws DataIntegrityException Caso a votação associada ao voto já tenha sido encerrada 
	 * @throws UnbaleVoteException Caso o voto já tenha sido computado anteriormente ou associado não tenha autorização de votar
	 */
	public Voto save(Voto voto)  throws DataIntegrityException, UnbaleVoteException, ThirdPartException;
	
	/**
	 * Remove um voto com base no identificador informado 
	 * @param id o identificador do voto
	 * @throws ResourceNotFoundException Caso não encontre voto relacionada ao identificador informado
	 */
	public void delete(int id) throws ResourceNotFoundException;
	
	/**
	 * Retorna a lista de votos associados a uma votação
	 * @param idVotacao O identificador da votação
	 * @return A lista de votos relacionados ao identificador infomado
	 */
	public List<Voto> getByVotacaoId(int idVotacao);
	
	/**
	 * Restorna a contagem de votos favoráveis e desfavoráveis de uma votação
	 * @param votacao A votação a ser processada
	 * @return Mapa com os totais de votos favoráveis e desfavoráveis
	 */
	public Map<Boolean, BigInteger> countVotos(Votacao votacao) throws AccessDeniedException;

}
