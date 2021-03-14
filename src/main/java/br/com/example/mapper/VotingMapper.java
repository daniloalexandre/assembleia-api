package br.com.example.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.example.dto.VotingResponseDTO;
import br.com.example.model.Voting;

/**
 * Interface que define o comportamento do controlador de mapeamento de
 * propriedades entre entridades relativas à votação.
 * 
 * @author Danilo Alexandre
 *
 */
@Mapper
public interface VotingMapper {
	VotingMapper INSTANCE = Mappers.getMapper(VotingMapper.class);

	/**
	 * Mapeia as propriedades de uma votação para o DTO de resposta
	 * 
	 * @param entity A votação
	 * @return o DTO de resposta
	 */
	VotingResponseDTO toResponseDTO(Voting entity);

}
