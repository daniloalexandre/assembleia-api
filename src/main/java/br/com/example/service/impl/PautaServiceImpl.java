package br.com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.example.exception.ResourceNotFoundException;
import br.com.example.l10n.APIMessages;
import br.com.example.model.Pauta;
import br.com.example.repository.PautaRepository;
import br.com.example.service.PautaService;

/**
 * Implementação dos serviços relativos à pauta.
 * 
 * @author Danilo Alexandre
 *
 */
@Service
@Transactional
public class PautaServiceImpl implements PautaService {

	@Autowired
	private PautaRepository repository;

	/**
	 * @see PautaService
	 */
	@Override
	public List<Pauta> getAll() {
		return repository.findAll();
	}

	/**
	 * @see PautaService
	 */
	@Override
	public Pauta get(int id) throws ResourceNotFoundException {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(APIMessages.PAUTA_NOT_FOUND.getCode(), id));
	}

	/**
	 * @see PautaService
	 */
	@Override
	public Pauta save(Pauta pauta) {
		return repository.save(pauta);
	}

	/**
	 * @see PautaService
	 */
	@Override
	public void delete(int id) throws ResourceNotFoundException {
		if (repository.existsById(id)) {
			repository.deleteById(id);
		} else {
			throw new ResourceNotFoundException(APIMessages.PAUTA_NOT_FOUND.getCode(), id);
		}

	}

}
