package br.com.example.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.example.dto.PautaRequestDTO;
import br.com.example.dto.PautaResponseDTO;
import br.com.example.model.Pauta;
/**
 * Interface que define o comportamento do controlador de mapeamento de propriedades entre entridades relativas à pauta.
 * 
 * @author Danilo Alexandre
 *
 */
@Mapper
public interface PautaMapper {
	PautaMapper INSTANCE = Mappers.getMapper( PautaMapper.class );
	/**
	 * Mapeia as propriedades de uma pauta para o DTO de resposta
	 * @param entity A pauta
	 * @return o DTO de resposta
	 */
	PautaResponseDTO toResponseDTO(Pauta entity);
	
	/**
	 * Mapeia as propriedades do DTO de requisição para uma pauta 
	 * @param requestDTO o DTO de requisição
	 * @return A pauta
	 */
	Pauta toEntity(PautaRequestDTO requestDTO);

}
