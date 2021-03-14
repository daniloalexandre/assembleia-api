package br.com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.example.async.VotingTasks;
import br.com.example.exception.ResourceNotFoundException;
import br.com.example.l10n.APIMessages;
import br.com.example.model.Agenda;
import br.com.example.model.Voting;
import br.com.example.repository.VotingRepository;
import br.com.example.service.VotingService;

@Service
@Transactional
public class VotingServiceImpl implements VotingService {

	@Autowired
	private VotingRepository repository;

	@Autowired
	private VotingTasks votingTasks;

	/**
	 * @see VotingService
	 */
	@Override
	public List<Voting> getAll() {
		return repository.findAll();
	}

	/**
	 * @see VotingService
	 */
	@Override
	public Voting get(int id) throws ResourceNotFoundException {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(APIMessages.VOTING_NOT_FOUND.getCode(), id));
	}

	/**
	 * @see VotingService
	 */
	@Override
	public Voting save(Agenda agenda, long duration) {

		Voting voting = Voting.createVoting(agenda, duration);
		votingTasks.processResultAfterMinutes(voting, duration);
		return repository.save(voting);
	}

	/**
	 * @see VotingService
	 */
	@Override
	public void delete(int id) throws ResourceNotFoundException {

		if (repository.existsById(id)) {
			repository.deleteById(id);
		} else {
			throw new ResourceNotFoundException(APIMessages.VOTING_NOT_FOUND.getCode(), id);
		}

	}

}
