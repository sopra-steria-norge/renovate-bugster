package no.soprasteria.bugster.infrastructure.db.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    List<T> list();

    Optional<T> findById(int id);

    void insert(T insert);

    void update(T update);
}
