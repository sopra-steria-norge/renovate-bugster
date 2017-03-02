package no.soprasteria.bugster.infrastructure.db.repository;

import jersey.repackaged.com.google.common.base.Preconditions;
import no.soprasteria.bugster.business.match.domain.FootballMatch;
import no.soprasteria.bugster.business.match.domain.Match;
import no.soprasteria.bugster.business.match.domain.Score;
import no.soprasteria.bugster.business.team.domain.Team;
import no.soprasteria.bugster.infrastructure.db.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MatchRepository {

    private static final Logger log = LoggerFactory.getLogger(MatchRepository.class);
    private final Database database;

    public MatchRepository(Database database) {
        Preconditions.checkNotNull(database);
        this.database = database;
    }

    public List<Match> list() {
        return database.queryForList("SELECT m.id ,m.status, m.start_date, ht.name as home_team, at.name as away_team, s.home, s.away, s.id as scoreId, s.home_penalties, s.away_penalties, s.home_extratime, s.away_extratime " +
                "FROM Match m " +
                "INNER JOIN Team ht ON m.home_team = ht.id " +
                "INNER JOIN Team at ON m.away_team = at.id " +
                "INNER JOIN Score s ON m.score = s.id", this::toFootballMatch);
    }

    public Optional<Match> findById(int id) {
        return database.queryForSingle("SELECT m.id ,m.status, m.start_date, ht.name as home_team, at.name as away_team, s.home, s.away, s.id as scoreId, s.home_penalties, s.away_penalties, s.home_extratime, s.away_extratime " +
                "FROM Match m " +
                "INNER JOIN Team ht ON m.home_team = ht.id " +
                "INNER JOIN Team at ON m.away_team = at.id " +
                "INNER JOIN Score s ON m.score = s.id" +
                "WHERE m.id = ?", id, this::toFootballMatch);
    }

    public List<Match> findByName(String name) {
        return database.queryForList("SELECT m.id, m.status, m.start_date, ht.name as home_team, at.name as away_team, s.home, s.away, s.id as scoreId, s.home_penalties, s.away_penalties, s.home_extratime, s.away_extratime " +
                "FROM Match m " +
                "INNER JOIN Team ht ON m.home_team = ht.id " +
                "INNER JOIN Team at ON m.away_team = at.id " +
                "INNER JOIN Score s ON m.score = s.id" +
                "WHERE ht.name = ? OR at.name = ?", this::toFootballMatch, name, name);
    }

    public List<Match> findByDate(String date) {
        return database.queryForList("SELECT m.id ,m.status, m.start_date, ht.name as home_team, at.name as away_team, s.home, s.away, s.id as scoreId, s.home_penalties, s.away_penalties, s.home_extratime, s.away_extratime " +
                "FROM Match m " +
                "INNER JOIN Team ht ON m.home_team = ht.id " +
                "INNER JOIN Team at ON m.away_team = at.id " +
                "INNER JOIN Score s ON m.score = s.id" +
                "WHERE start_date LIKE ?", this::toFootballMatch, "%" + date + "%");
    }

    public void insert(Match insert) {
        FootballMatch match = (FootballMatch) insert;
        database.doInTransaction(() -> {
            Score score = match.getScore();
            int scoreId = database.insert("insert into score (home, away, home_extratime, away_extratime, home_penalties, away_penalties) values (?, ?, ?, ?)", score.getHome(), score.getAway(), score.getHomeExtraTime(), score.getAwayExtraTime(), score.getHomePenalties(), score.getAwayPenalties());
            score.setId(scoreId);
            int matchId = database.insert("INSERT INTO match (home_team, away_team, score, start_date, status) VALUES (?, ?, ?, ?)", match.getHomeTeam().getId(), match.getAwayTeam().getId(), scoreId, match.getStartDate(), match.getStatus());
            match.setId(matchId);
        });
    }

    public void update(Match match) {
        throw new NotImplementedException();
    }

    private Match toFootballMatch(Database.Row row) throws SQLException {
        Team awayTeam = new Team(row.getString("away_team"));
        Team homeTeam = new Team(row.getString("home_team"));
        Score score = new Score(row.getInt("home"), row.getInt("away"));
        score.setHomePenalties(row.getInt("home_penalties"));
        score.setAwayPenalties(row.getInt("away_penalties"));
        score.setHomeExtraTime(row.getInt("home_extratime"));
        score.setAwayExtraTime(row.getInt("away_extratime"));
        score.setId(row.getInt("scoreId"));
        FootballMatch match = new FootballMatch(homeTeam, awayTeam, score, row.getString("status"), row.getString("start_date"));
        match.setId(row.getInt("id"));
        return match;
    }

}
