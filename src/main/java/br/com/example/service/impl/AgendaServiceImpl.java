package br.com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.example.exception.ResourceNotFoundException;
import br.com.example.l10n.APIMessages;
import br.com.example.model.Agenda;
import br.com.example.repository.AgendaRepository;
import br.com.example.service.AgendaService;

/**
 * Implementação dos serviços relativos à pauta.
 * 
 * @author Danilo Alexandre
 *
 */
@Service
@Transactional
public class AgendaServiceImpl implements AgendaService {

	@Autowired
	private AgendaRepository repository;

	/**
	 * @see AgendaService
	 */
	@Override
	public List<Agenda> getAll() {
		return repository.findAll();
	}

	/**
	 * @see AgendaService
	 */
	@Override
	public Agenda get(int id) throws ResourceNotFoundException {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(APIMessages.AGENDA_NOT_FOUND.getCode(), id));
	}

	/**
	 * @see AgendaService
	 */
	@Override
	public Agenda save(Agenda pauta) {
		return repository.save(pauta);
	}

	/**
	 * @see AgendaService
	 */
	@Override
	public void delete(int id) throws ResourceNotFoundException {
		if (repository.existsById(id)) {
			repository.deleteById(id);
		} else {
			throw new ResourceNotFoundException(APIMessages.AGENDA_NOT_FOUND.getCode(), id);
		}

	}

}
