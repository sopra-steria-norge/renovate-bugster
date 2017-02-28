package no.soprasteria.bugster.infrastructure.db.repository;

import no.soprasteria.bugster.business.transaction.domain.Transaction;
import no.soprasteria.bugster.infrastructure.db.Database;
import org.slf4j.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.Optional;

public class TransactionRepository extends Repository<Transaction> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(TransactionRepository.class);

    TransactionRepository() {
        super();
    }

    @Override
    public List<Transaction> list() {
        return database.queryForList("SELECT * " +
                "FROM Transaction t ", null);
    }

    @Override
    public Optional<Transaction> findById(int id) {
        return database.queryForSingle("SELECT * FROM TRANSACTION WHERE id = ?", id, this::toTransaction);
    }

    @Override
    public void insert(Transaction insert) {
        database.doInTransaction(() -> {
            database.insert("INSERT INTO Transaction ");
        });
    }

    @Override
    public void update(Transaction update) {
        throw new NotImplementedException();
    }

    private Transaction toTransaction(Database.Row row) {
        return null;
    }
}
