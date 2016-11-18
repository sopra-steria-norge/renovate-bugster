package no.soprasteria.bugster.business.team.service;

import no.soprasteria.bugster.business.team.domain.Team;
import no.soprasteria.bugster.infrastructure.db.repository.TeamRepository;

import java.util.List;
import java.util.Optional;

public class TeamService {

    private TeamRepository repository;

    public TeamService(TeamRepository repository) {
        this.repository = repository;
    }

    public List<Team> findAll() {
        return repository.list();
    }

    public Optional<Team> findByName(String name) {
        return repository.findByName(name);
    }
}
