package no.soprasteria.bugster.business.match.service;

import no.soprasteria.bugster.business.match.domain.Match;
import no.soprasteria.bugster.infrastructure.db.repository.MatchRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MatchService {

    private MatchRepository repository;

    public MatchService(MatchRepository repository) {
        this.repository = repository;
    }

    public MatchService() {
    }

    public List<Match> findAll() {
        return repository.list();
    }

    public Optional<Match> findById(int id) {
        return repository.findById(id);
    }
}
