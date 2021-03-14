package br.com.example.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.example.dto.AgendaRequestDTO;
import br.com.example.dto.AgendaResponseDTO;
import br.com.example.model.Agenda;

/**
 * Interface que define o comportamento do controlador de mapeamento de
 * propriedades entre entridades relativas à pauta.
 * 
 * @author Danilo Alexandre
 *
 */
@Mapper
public interface AgendaMapper {
	AgendaMapper INSTANCE = Mappers.getMapper(AgendaMapper.class);

	/**
	 * Mapeia as propriedades de uma pauta para o DTO de resposta
	 * 
	 * @param entity A pauta
	 * @return o DTO de resposta
	 */
	AgendaResponseDTO toResponseDTO(Agenda entity);

	/**
	 * Mapeia as propriedades do DTO de requisição para uma pauta
	 * 
	 * @param requestDTO o DTO de requisição
	 * @return A pauta
	 */
	Agenda toEntity(AgendaRequestDTO requestDTO);

}
