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
    public void insert(Bet bet) {
//        database.doInTransaction(() -> {
//            int betId = database.insert("insert into bet (matchId, result) values (?, ?)"
//                    ,bet.getMatchId()
//                    ,bet.getBettedResult());
//            bet.setId(betId);
//        });
    }

    @Override
    public void update(Bet update) {

    }
}
