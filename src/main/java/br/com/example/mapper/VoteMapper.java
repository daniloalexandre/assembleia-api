package br.com.example.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.example.dto.VoteRequestDTO;
import br.com.example.dto.VoteResponseDTO;
import br.com.example.model.Vote;

/**
 * Interface que define o comportamento do controlador de mapeamento de
 * propriedades entre entridades relativas ao voto.
 * 
 * @author Danilo Alexandre
 *
 */
@Mapper
public interface VoteMapper {
	VoteMapper INSTANCE = Mappers.getMapper(VoteMapper.class);

	/**
	 * Mapeia as propriedades de um voto para o DTO de resposta
	 * 
	 * @param entity O voto
	 * @return o DTO de resposta
	 */
	VoteResponseDTO toResponseDTO(Vote entity);

	/**
	 * Mapeia as propriedades do DTO de requisição para um voto
	 * 
	 * @param requestDTO o DTO de requisição
	 * @return O voto
	 */
	Vote toEntity(VoteRequestDTO requestDTO);

}
