package br.com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.example.exception.ResourceNotFoundException;
import br.com.example.l10n.APIMessages;
import br.com.example.model.Votacao;
import br.com.example.repository.VotacaoRepository;
import br.com.example.service.VotacaoService;

@Service
@Transactional
public class VotacaoServiceImpl implements VotacaoService {

	@Autowired
	private VotacaoRepository repository;

	/**
	 * @see VotacaoService
	 */
	@Override
	public List<Votacao> getAll() {
		return repository.findAll();
	}

	/**
	 * @see VotacaoService
	 */
	@Override
	public Votacao get(int id) throws ResourceNotFoundException {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(APIMessages.VOTACAO_NOT_FOUND.getCode(), id));
	}

	/**
	 * @see VotacaoService
	 */
	@Override
	public Votacao save(Votacao votacao) {
		return repository.save(votacao);
	}

	/**
	 * @see VotacaoService
	 */
	@Override
	public void delete(int id) throws ResourceNotFoundException {

		if (repository.existsById(id)) {
			repository.deleteById(id);
		} else {
			throw new ResourceNotFoundException(APIMessages.VOTACAO_NOT_FOUND.getCode(), id);
		}

	}

}
