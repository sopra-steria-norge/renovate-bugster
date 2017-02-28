package no.soprasteria.bugster.infrastructure.db.repository;

import no.soprasteria.bugster.business.bet.domain.Odds;
import no.soprasteria.bugster.infrastructure.db.Database;
import org.slf4j.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.Optional;

public class OddsRepository extends Repository<Odds> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(OddsRepository.class);

    public OddsRepository() {
        super();
    }

    @Override
    public List<Odds> list() {
        return database.queryForList("SELECT * FROM USER", this::toOdds);
    }

    @Override
    public Optional<Odds> findById(int id) {
        return database.queryForSingle("SELECT * FROM USER WHERE id = ?", id, this::toOdds);
    }

    @Override
    public void insert(Odds insert) {

    }

    @Override
    public void update(Odds update) {
        throw new NotImplementedException();
    }

    private Odds toOdds(Database.Row row) {
        return null;
    }
}
