package no.soprasteria.bugster.infrastructure.db.repository;

import no.soprasteria.bugster.business.team.domain.Team;
import no.soprasteria.bugster.infrastructure.db.Database;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TeamRepository extends Repository<Team> {

    TeamRepository() {
        super();
    }

    @Override
    public List<Team> list() {
        return database.queryForList("SELECT * FROM TEAM", this::toTeam);
    }

    @Override
    public Optional<Team> findById(int id) {
        return database.queryForSingle("SELECT * FROM TEAM WHERE id = ?", id, this::toTeam);
    }

    public Optional<Team> findByName(String name) {
        return database.queryForSingle("SELECT * FROM TEAM WHERE name = ?", name, this::toTeam);
    }

    private Team toTeam(Database.Row row) throws SQLException {
        Team team = new Team(row.getString("name"));
        team.setId(row.getInt("id"));
        return team;
    }

    @Override
    public void insert(Team team) {
        database.doInTransaction(() -> {
            int insert = database.insert("INSERT INTO TEAM (name) values (?)", team.getName());
            team.setId(insert);
        });
    }

    @Override
    public void update(Team update) {
        throw new NotImplementedException();
    }

    public Team validate(List<Team> list, int i) {
        try {
            return list.get(i);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidTeamException();
        }

    }
}
