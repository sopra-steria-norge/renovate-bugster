package no.soprasteria.bugster.business.match.service;

import no.soprasteria.bugster.business.match.domain.FootballMatch;
import no.soprasteria.bugster.business.match.domain.Match;
import no.soprasteria.bugster.business.polling.service.ResultService;
import no.soprasteria.bugster.infrastructure.db.repository.MatchRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MatchService {

    private MatchRepository repository;

    public MatchService(MatchRepository repository) {
        this.repository = repository;
    }

    public List<FootballMatch> findAll() {
        List<FootballMatch> list = repository.list();
        Collections.sort(list);
        return list;
    }

    public List<FootballMatch> findAllByName(String name) {
        List<FootballMatch> byName = repository.findByName(name);
        Collections.sort(byName);
        return byName;
    }

    public List<FootballMatch> findAllByDate(String name) {
        List<FootballMatch> byName = repository.findByName(name);
        Collections.sort(byName);
        return byName;
    }

    public Optional<FootballMatch> findById(int id) {
        return repository.find(id);
    }
}
