package no.soprasteria.bugster.infrastructure.db;

import no.soprasteria.bugster.infrastructure.exception.RequestException;

import java.sql.SQLException;
import java.util.function.Supplier;

public class Repository<T> {

    protected Supplier<RequestException> notFound(Class<?> clazz, int id) {
        String className = clazz.getName();
        return () -> new RequestException(404, "Can't find " + className + " with id " + id);
    }

    protected T toDomainObject(Database.Row row) throws SQLException { throw new UnsupportedOperationException(); }
}
