package br.com.example.service.impl;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.example.exception.AccessDeniedException;
import br.com.example.exception.DataIntegrityException;
import br.com.example.exception.ResourceNotFoundException;
import br.com.example.exception.ThirdPartException;
import br.com.example.exception.UnbaleVoteException;
import br.com.example.l10n.APIMessages;
import br.com.example.model.Votacao;
import br.com.example.model.Voto;
import br.com.example.repository.VotoRepository;
import br.com.example.service.VotoService;
import br.com.example.webclient.AssociadoResponse;
import br.com.example.webclient.AssociadoWebClient;

@Service
@Transactional
public class VotoServiceImpl implements VotoService {

	@Autowired
	private VotoRepository repository;

	@Autowired
	private AssociadoWebClient webClient;

	/**
	 * @see VotoService
	 */
	@Override
	public List<Voto> getAll() {
		return repository.findAll();
	}

	/**
	 * @see VotoService
	 */
	@Override
	public Voto get(int id) throws ResourceNotFoundException {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(APIMessages.VOTO_NOT_FOUND.getCode(), id));
	}

	/**
	 * @see VotoService
	 */
	@Override
	public Voto save(Voto voto) throws DataIntegrityException, UnbaleVoteException, ThirdPartException {

		Voto votoDB = repository.findByCPFAndVotacaoId(voto.getCpf(), voto.getVotacao().getId());
		if (votoDB != null)
			throw new UnbaleVoteException(APIMessages.VOTO_ALREADY_COMPUTED.getCode());

		if (voto.getTimestamp().isAfter(voto.getVotacao().getPrazo()))
			throw new DataIntegrityException(APIMessages.VOTO_EXPIRED_PERIOD.getCode());
		
		AssociadoResponse response;
		try {
			response = webClient.consultaAssociado(voto.getCpf());
		}catch (Exception e) {
			throw new ThirdPartException(APIMessages.THIRD_PART_ERROR.getCode());
		}		
		if (response != null && response.getStatus().contentEquals(AssociadoResponse.UNABLE_TO_VOTE))
			throw new UnbaleVoteException(APIMessages.ASSOCIADO_UNABLE.getCode(), voto.getCpf());

		Voto savedVoto = repository.save(voto);

		return savedVoto;
	}

	/**
	 * @see VotoService
	 */
	@Override
	public void delete(int id) throws ResourceNotFoundException {
		if (repository.existsById(id)) {
			repository.deleteById(id);
		} else {
			throw new ResourceNotFoundException(APIMessages.VOTO_NOT_FOUND.getCode(), id);
		}
	}

	/**
	 * @see VotoService
	 */
	@Override
	public List<Voto> getByVotacaoId(int idVotacao) {
		return repository.getByVotacaoId(idVotacao);
	}

	/**
	 * @throws AccessDeniedException
	 * @see VotoService
	 */
	@Override
	public Map<Boolean, BigInteger> countVotos(Votacao votacao) throws AccessDeniedException {

		if (votacao.getPrazo().isAfter(LocalDateTime.now()))
			throw new AccessDeniedException(APIMessages.VOTACAO_NOT_EXPIRED.getCode());

		return repository.countVotos(votacao);
	}

}
