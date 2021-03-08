package br.com.example.repository;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.example.model.Votacao;

/**
 * Classe implementa o comportamento específico do gerenciador de dados da entidade voto
 * 
 * @author Danilo Alexandre
 *
 */
@Repository
public class VotoRepositoryCustomImpl implements VotoRepositoryCustom {
	
	@PersistenceContext
    private EntityManager em;

	/**
	 * @see VotoRepositoryCustom.countVotos
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<Boolean, BigInteger> countVotos(Votacao votacao) {
		
		Map<Boolean, BigInteger> result = new HashMap<Boolean, BigInteger>();
		String sql = new StringBuilder()
				.append("select vt.favoravel, count(*) ")
				.append("from Voto vt ")
				.append("inner join votacao v on (v.id_votacao = vt.id_votacao) ")
				.append("where v.id_votacao = :id_votacao ")
				.append("group by vt.favoravel  ").toString();
		
		Query nativeQuery = em.createNativeQuery(sql);
        nativeQuery.setParameter("id_votacao", votacao.getId());
		nativeQuery.getResultList().forEach(item -> {
			Object[] row = (Object[]) item;
			result.put( (Boolean) row[0], (BigInteger) row[1]);
		});
		
		return result;

		

	}

}
