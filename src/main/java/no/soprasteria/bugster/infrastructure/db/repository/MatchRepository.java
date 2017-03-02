package no.soprasteria.bugster.infrastructure.db.repository;

import no.soprasteria.bugster.business.match.domain.FootballMatch;
import no.soprasteria.bugster.business.match.domain.Match;
import no.soprasteria.bugster.business.match.domain.Score;
import no.soprasteria.bugster.business.team.domain.Team;
import no.soprasteria.bugster.infrastructure.db.Database;
import org.slf4j.Logger;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

public class MatchRepository extends Repository<Match> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(MatchRepository.class);

    MatchRepository() {
        super();
    }

    @Override
    public List<Match> list() {
        return database.queryForList("SELECT m.id ,m.status, m.start_date, home_team, away_team, s.home, s.away, s.id as scoreId " +
                "FROM Match m " +
                "INNER JOIN Score s ON m.score = s.id " +
                "ORDER BY m.start_date", this::toFootballMatch);
    }

    @Override
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

    @Override
    public void insert(Match insert) {
        FootballMatch match = (FootballMatch) insert;
        database.doInTransaction(() -> {
            Score score = match.getScore();
            int scoreId = database.insert("insert into score (home, away) values (?, ?)", score.getHome(), score.getAway());
            score.setId(scoreId);
            int matchId = database.insert("INSERT INTO match (home_team, away_team, score, start_date, status) VALUES (?, ?, ?, ?, ?)", match.getHomeTeam().getName(), match.getAwayTeam().getName(), scoreId, match.getStartDate(), match.getStatus());
            match.setId(matchId);
        });
    }

    @Override
    public void update(Match match) {
        database.doInTransaction(() -> {
            Score score = match.getScore();
            Optional<Integer> id = database.queryForSingle("select score from match where id =?", match.getId(), r -> r.getInt("score"));
            if(!id.isPresent()){
                throw new IllegalArgumentException("Noe gikk galt... ");
            }else{
                database.executeOperation("update score set home = ?, away = ? where id = ?", score.getHome(), score.getAway(), id.get());
            }
        });
    }

    private Match toFootballMatch(Database.Row row) throws SQLException {
        Team awayTeam = new Team(row.getString("away_team"));
        Team homeTeam = new Team(row.getString("home_team"));
        Score score = new Score(row.getInt("home"), row.getInt("away"));
        score.setId(row.getInt("scoreId"));
        FootballMatch match = new FootballMatch(homeTeam, awayTeam, score, row.getString("status"), LocalDateTime.ofInstant(row.getInstant("start_date"), ZoneId.of("UTC")));
        match.setId(row.getInt("id"));
        return match;
    }

}
