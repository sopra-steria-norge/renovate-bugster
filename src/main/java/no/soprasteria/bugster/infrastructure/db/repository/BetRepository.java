package no.soprasteria.bugster.infrastructure.db.repository;

import no.soprasteria.bugster.business.bet.domain.Bet;

import java.util.List;
import java.util.Optional;

public class BetRepository extends Repository<Bet> {

    @Override
    public List<Bet> list() {
        return null;
    }

    @Override
    public Optional<Bet> findById(int id) {
        return null;
    }

    @Override
    public void insert(Bet insert) {

    }

    @Override
    public void update(Bet update) {

    }
}
