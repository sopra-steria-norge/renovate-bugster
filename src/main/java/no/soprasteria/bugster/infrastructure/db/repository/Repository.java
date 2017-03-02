package no.soprasteria.bugster.infrastructure.db.repository;

import no.soprasteria.bugster.application.server.ReloadableAppConfigFile;
import no.soprasteria.bugster.infrastructure.db.Database;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

public abstract class Repository<T> {

    final Database database;

    Repository() {
        this.database = ReloadableAppConfigFile.getInstance().getDatabase();
    }

    public abstract List<T> list();

    public abstract Optional<T> findById(int id);

    public abstract void insert(T insert);

    public abstract void update(T update);
}
