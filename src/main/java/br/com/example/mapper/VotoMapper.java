package br.com.example.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.example.dto.VotoRequestDTO;
import br.com.example.dto.VotoResponseDTO;
import br.com.example.model.Voto;

/**
 * Interface que define o comportamento do controlador de mapeamento de propriedades entre entridades relativas ao voto.
 * 
 * @author Danilo Alexandre
 *
 */
@Mapper
public interface VotoMapper {
	VotoMapper INSTANCE = Mappers.getMapper( VotoMapper.class );
	
	/**
	 * Mapeia as propriedades de um voto para o DTO de resposta
	 * @param entity O voto
	 * @return o DTO de resposta
	 */
	VotoResponseDTO toResponseDTO(Voto entity);
	
	/**
	 * Mapeia as propriedades do DTO de requisição para um voto
	 * @param requestDTO o DTO de requisição
	 * @return O voto
	 */
	Voto toEntity(VotoRequestDTO requestDTO);

}
