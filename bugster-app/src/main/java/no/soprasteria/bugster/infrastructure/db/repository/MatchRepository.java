package no.soprasteria.bugster.infrastructure.db.repository;

import no.soprasteria.bugster.business.match.domain.FootballMatch;
import no.soprasteria.bugster.business.match.domain.OldMatchStatus;
import no.soprasteria.bugster.business.match.domain.Score;
import no.soprasteria.bugster.business.team.domain.Team;
import no.soprasteria.bugster.infrastructure.db.Database;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MatchRepository {
    private final Database database;

    public MatchRepository(Database database) {
        this.database = database;
    }

    public List<FootballMatch> list() {
        return database.queryForList("SELECT m.id ,m.status, ht.name as home_team, at.name as away_team, s.home, s.away, s.id as scoreId, s.homePenalties, s.awayPenalties " +
                "FROM Match m " +
                "INNER JOIN Team ht ON m.home_team = ht.id " +
                "INNER JOIN Team at ON m.away_team = at.id " +
                "INNER JOIN Score s ON m.score = s.id", this::toFootballMatch);
    }
    public void insert(FootballMatch match) {
        database.doInTransaction(() -> {
            Score score = match.getScore();
            int scoreId = database.insert("insert into score (home, away, home_penalties, away_penalties) values (?, ?, ?, ?) returning id", score.getHome(), score.getAway(), score.getHomePenalties(), score.getAwayPenalties());
            score.setId(scoreId);
            int matchId = database.insert("INSERT INTO match (home_team, away_team, score, status) VALUES (?, ?, ?, ?) returning id", match.getHomeTeam().getId(), match.getAwayTeam().getId(), scoreId, match.getStatus().getCssClass());
            match.setId(matchId);
        });
    }

    public void update(int orderId, FootballMatch order) {
        database.doInTransaction(() -> {
            updateMatch(orderId, order);
        });
    }

    private void updateMatch(int matchId, FootballMatch footballMatch) {

    }

    private FootballMatch toFootballMatch(Database.Row row) throws SQLException {
        Team awayTeam = new Team(row.getString("away_team"));
        Team homeTeam = new Team(row.getString("home_team"));
        Score score = new Score(row.getInt("home"), row.getInt("away"));
        score.setHomePenalties(row.getInt("home_penalties"));
        score.setAwayPenalties(row.getInt("away_penalties"));
        score.setId(row.getInt("scoreId"));
        FootballMatch match = new FootballMatch(homeTeam, awayTeam, score, OldMatchStatus.valueOf(row.getString("status")));
        match.setId(row.getInt("id"));
        return match;
    }

    public Optional<FootballMatch> find(int id) {
        return database.queryForSingle("SELECT m.id ,m.status, ht.name as home_team, at.name as away_team, s.home, s.away, s.id as scoreId, s.homePenalties, s.awayPenalties " +
                "FROM Match m " +
                "INNER JOIN Team ht ON m.home_team = ht.id " +
                "INNER JOIN Team at ON m.away_team = at.id " +
                "INNER JOIN Score s ON m.score = s.id" +
                "WHERE m.id = ?", id, this::toFootballMatch);
    }
}
