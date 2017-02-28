package no.soprasteria.bugster.infrastructure.db.repository;

import no.soprasteria.bugster.business.user.domain.User;
import no.soprasteria.bugster.infrastructure.db.Database;
import org.slf4j.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.Optional;

public class UserRepository extends Repository<User> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(UserRepository.class);

    UserRepository() {
        super();
    }

    @Override
    public List<User> list() {
        return database.queryForList("SELECT * FROM USER", this::toUser);
    }

    @Override
    public Optional<User> findById(int id) {
        return database.queryForSingle("SELECT * FROM USER WHERE id = ?", id, this::toUser);
    }

    @Override
    public void insert(User insert) {

    }

    @Override
    public void update(User update) {
        throw new NotImplementedException();
    }

    private User toUser(Database.Row row) {
        return null;
    }
}
