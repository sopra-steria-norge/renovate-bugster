package no.soprasteria.bugster.infrastructure.db.repository;

import no.soprasteria.bugster.business.team.domain.Team;
import no.soprasteria.bugster.infrastructure.db.Database;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TeamRepository {

    private final Database database;

    public TeamRepository(Database database) {
        this.database = database;
    }

    public List<Team> findAll() {
        return database.queryForList("SELECT * FROM TEAM", this::toTeam);
    }

    public Optional<Team> findByName(String name) {
        return database.queryForSingle("SELECT * FROM TEAM WHERE name = ?", name, this::toTeam);
    }

    private Team toTeam(Database.Row row) throws SQLException {
        Team team = new Team(row.getString("name"));
        team.setId(row.getInt("id"));
        return team;
    }
}
