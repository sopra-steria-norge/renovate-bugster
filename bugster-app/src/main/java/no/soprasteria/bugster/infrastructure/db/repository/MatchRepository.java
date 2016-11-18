package no.soprasteria.bugster.infrastructure.db.repository;

import no.soprasteria.bugster.business.match.domain.FootballMatch;
import no.soprasteria.bugster.business.match.domain.Score;
import no.soprasteria.bugster.business.team.domain.Team;
import no.soprasteria.bugster.infrastructure.db.Database;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MatchRepository {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MatchRepository.class);
    private final Database database;

    public MatchRepository(Database database) {
        this.database = database;
    }

    public List<FootballMatch> list() {
        return database.queryForList("SELECT m.id ,m.status, ht.name as home_team, at.name as away_team, s.home, s.away, s.id as scoreId, s.home_penalties, s.away_penalties, s.home_extratime, s.away_extratime " +
                "FROM Match m " +
                "INNER JOIN Team ht ON m.home_team = ht.id " +
                "INNER JOIN Team at ON m.away_team = at.id " +
                "INNER JOIN Score s ON m.score = s.id", this::toFootballMatch);
    }

    public void insert(FootballMatch match) throws SQLException {
        try {
            database.doInTransaction(() -> {
                Score score = match.getScore();
                int scoreId = database.insert("insert into score (home, away, home_extratime, away_extratime, home_penalties, away_penalties) values (?, ?, ?, ?)", score.getHome(), score.getAway(), score.getHomeExtraTime(), score.getAwayExtraTime(), score.getHomePenalties(), score.getAwayPenalties());
                score.setId(scoreId);
                int matchId = database.insert("INSERT INTO match (home_team, away_team, score, status) VALUES (?, ?, ?, ?)", match.getHomeTeam().getId(), match.getAwayTeam().getId(), scoreId, match.getStatus());
                match.setId(matchId);
            });
        } catch (Exception e) {
            log.warn("Failed to save match." + match);
        }
    }

    public void update(int id, FootballMatch match) {
        throw new NotImplementedException();
    }

    private FootballMatch toFootballMatch(Database.Row row) throws SQLException {
        Team awayTeam = new Team(row.getString("away_team"));
        Team homeTeam = new Team(row.getString("home_team"));
        Score score = new Score(row.getInt("home"), row.getInt("away"));
        score.setHomePenalties(row.getInt("home_penalties"));
        score.setAwayPenalties(row.getInt("away_penalties"));
        score.setHomeExtraTime(row.getInt("home_extratime"));
        score.setAwayExtraTime(row.getInt("away_extratime"));
        score.setId(row.getInt("scoreId"));
        FootballMatch match = new FootballMatch(homeTeam, awayTeam, score, row.getString("status"));
        match.setId(row.getInt("id"));
        return match;
    }

    public Optional<FootballMatch> find(int id) {
        return database.queryForSingle("SELECT m.id ,m.status, ht.name as home_team, at.name as away_team, s.home, s.away, s.id as scoreId, s.home_penalties, s.away_penalties, s.home_extratime, s.away_extratime " +
                "FROM Match m " +
                "INNER JOIN Team ht ON m.home_team = ht.id " +
                "INNER JOIN Team at ON m.away_team = at.id " +
                "INNER JOIN Score s ON m.score = s.id" +
                "WHERE m.id = ?", id, this::toFootballMatch);
    }

    public List<FootballMatch> findByName(String name) {
        return database.queryForList("SELECT m.id ,m.status, ht.name as home_team, at.name as away_team, s.home, s.away, s.id as scoreId, s.home_penalties, s.away_penalties, s.home_extratime, s.away_extratime " +
                "FROM Match m " +
                "INNER JOIN Team ht ON m.home_team = ht.id " +
                "INNER JOIN Team at ON m.away_team = at.id " +
                "INNER JOIN Score s ON m.score = s.id" +
                "WHERE ht.name = ? OR at.name = ?", this::toFootballMatch, name, name);
    }
}
