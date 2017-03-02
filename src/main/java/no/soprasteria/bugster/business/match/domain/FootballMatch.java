package no.soprasteria.bugster.business.match.domain;


import no.soprasteria.bugster.business.team.domain.Team;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class FootballMatch implements Match {

    private Integer id;
    private Team homeTeam;
    private Team awayTeam;
    private Score score;
    private String status;
    private LocalDateTime startDate;

    public FootballMatch(Team homeTeam, Team awayTeam, Score score, String status, LocalDateTime startDate) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.status = status;
        this.score = score;
        this.startDate = startDate;
    }

    @Override
    public Team getHomeTeam() {
        return homeTeam;
    }

    @Override
    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    @Override
    public Team getAwayTeam() {
        return awayTeam;
    }

    @Override
    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    @Override
    public Score getScore() {
        return score;
    }

    @Override
    public void updateScore(Score score) {
        this.score.setHome(score.getHome());
        this.score.setAway(score.getAway());
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        String toString = "[" + homeTeam.getName() + " vs " + awayTeam.getName() + "] ";
        if(!Objects.equals(status, "not-started")) {
            return toString + score.toString();
        } else {
            return toString + " har ikke startet.";
        }
    }

    @Override
    public int compareTo(Match o) {
        int homeTeamCompare = getHomeTeam().getName().compareTo(o.getHomeTeam().getName());
        if (homeTeamCompare != 0 ) {
            return homeTeamCompare;
        } else {
            return getAwayTeam().getName().compareTo(getAwayTeam().getName());
        }
    }

}
