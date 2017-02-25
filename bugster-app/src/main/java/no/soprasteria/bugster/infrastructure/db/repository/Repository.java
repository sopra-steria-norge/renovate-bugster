package no.soprasteria.bugster.infrastructure.db.repository;

import java.util.List;

public interface Repository<T> {
    List<T> list();

    void insert(T insert);

    void update(T update);
}
