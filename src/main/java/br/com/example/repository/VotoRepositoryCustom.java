package br.com.example.repository;

import java.math.BigInteger;
import java.util.Map;

import br.com.example.model.Votacao;

/**
 * Interface que define o comportamento específico do gerenciador de dados da entidade voto
 * 
 * @author Danilo Alexandre
 *
 */
public interface VotoRepositoryCustom {
	
	/**
	 * Conta o total de votos favoráveis e desfavoráveis em uma votação
	 * @param votacao A votação a ser computada
	 * @return Um mapa com a contagem de votos favoráveis (Boolean true) e desfavoráveis (Boolean false)
	 */
	public Map<Boolean, BigInteger> countVotos(Votacao votacao);

}
