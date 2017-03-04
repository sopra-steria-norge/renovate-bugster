package no.soprasteria.bugster.infrastructure.db.repository;

import no.soprasteria.bugster.business.bet.domain.Bet;
import no.soprasteria.bugster.business.match.domain.Match;
import no.soprasteria.bugster.business.user.domain.User;
import no.soprasteria.bugster.infrastructure.db.Database;

import java.sql.SQLException;
import java.util.ArrayList;
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

    private Bet toBet(Database.Row row) throws SQLException {
        Bet bet = new Bet();
        int id = row.getInt("id");
        bet.setId(id);
        bet.setAmount(row.getInt("amount"));
        bet.setOdds(new OddsRepository().findById(id).get());
        bet.setMatch(new MatchRepository().findById(bet.getOdds().getId()).get());
        bet.setUser(new UserRepository().findById(row.getInt("user_id")).get());
        return bet;
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

    public List<Bet> listByUser(String name) {
        UserRepository userRepository = new UserRepository();
        User user = userRepository.findByName(name).get();
        return database.queryForList("select * from bet where user_id = ?", this::toBet, user.getId());
    }

    public List<Bet> listByMatch(Integer id) {
        List<Bet> matches = new ArrayList<>();
        for(int i = 0; i< list().size(); i++) {
            Bet bet = list().get(i);
            if(bet.getMatch().getId() == id){
                matches.add(bet);
            }
        }
        return matches;
    }
}
