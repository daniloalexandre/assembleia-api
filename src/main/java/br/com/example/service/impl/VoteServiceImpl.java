package br.com.example.service.impl;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.example.exception.DataIntegrityException;
import br.com.example.exception.ResourceNotFoundException;
import br.com.example.exception.ThirdPartException;
import br.com.example.exception.UnbaleVoteException;
import br.com.example.l10n.APIMessages;
import br.com.example.model.Vote;
import br.com.example.model.Voting;
import br.com.example.repository.VoteRepository;
import br.com.example.service.VoteService;
import br.com.example.webclient.AffiliatedResponse;
import br.com.example.webclient.AffiliatedWebClient;

@Service
@Transactional
public class VoteServiceImpl implements VoteService {

	@Autowired
	private VoteRepository repository;

	@Autowired
	private AffiliatedWebClient webClient;

	/**
	 * @see VoteService
	 */
	@Override
	public List<Vote> getAll() {
		return repository.findAll();
	}

	/**
	 * @see VoteService
	 */
	@Override
	public Vote get(int id) throws ResourceNotFoundException {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(APIMessages.VOTE_NOT_FOUND.getCode(), id));
	}

	/**
	 * @see VoteService
	 */
	@Override
	public Vote save(Voting voting, Vote vote) throws DataIntegrityException, UnbaleVoteException, ThirdPartException {

		vote.setVoting(voting);
		vote.setTimestamp(ZonedDateTime.now());

		Vote voteDB = repository.findByCpfAndVotingId(vote.getCpf(), vote.getVoting().getId());
		if (voteDB != null)
			throw new UnbaleVoteException(APIMessages.VOTE_ALREADY_COMPUTED.getCode());

		if (vote.getTimestamp().isAfter(vote.getVoting().getDeadline()))
			throw new DataIntegrityException(APIMessages.VOTE_EXPIRED_PERIOD.getCode());

		AffiliatedResponse response;
		try {
			response = webClient.consultaAssociado(vote.getCpf());
		} catch (Exception e) {
			throw new ThirdPartException(APIMessages.THIRD_PART_ERROR.getCode());
		}
		if (response != null && response.getStatus().contentEquals(AffiliatedResponse.UNABLE_TO_VOTE))
			throw new UnbaleVoteException(APIMessages.AFFILIATED_UNABLE.getCode(), vote.getCpf());

		Vote savedVoto = repository.save(vote);

		return savedVoto;
	}

	/**
	 * @see VoteService
	 */
	@Override
	public void delete(int id) throws ResourceNotFoundException {
		if (repository.existsById(id)) {
			repository.deleteById(id);
		} else {
			throw new ResourceNotFoundException(APIMessages.VOTE_NOT_FOUND.getCode(), id);
		}
	}

	/**
	 * @see VoteService
	 */
	@Override
	public List<Vote> getByVotingId(int idVoting) {
		return repository.getByVotingId(idVoting);
	}

	@Override
	public Long countByFavorableAndVotingId(boolean favorable, int idVoting) {
		return repository.countByFavorableAndVotingId(favorable, idVoting);
	}

}
