package no.soprasteria.bugster.infrastructure.db.repository;

import no.soprasteria.bugster.business.bet.domain.Bet;
import no.soprasteria.bugster.infrastructure.db.Database;

import java.util.List;
import java.util.Optional;

public class BetRepository extends Repository<Bet> {
    public BetRepository(){
        new UserRepository();
    }
    @Override
    public List<Bet> list() {
        return database.queryForList("select * from bet", this::toBet);
    }

    private <T> T toBet(Database.Row row) {
        return null;
    }

    @Override
    public Optional<Bet> findById(int id) {
        return null;
    }

    @Override
    public void insert(Bet bet) {
        database.doInTransaction(() -> {
            int betId = database.insert("insert into bet (odds_id, user_id, amount) values (?, ?, ?)",
                    bet.getOdds().getId(),
                    bet.getUser().getId(),
                    bet.getAmount());
            bet.setId(betId);
        });
    }

    @Override
    public void update(Bet update) {
        throw new UnsupportedOperationException("Dette er ikke tillatt");
    }
}
