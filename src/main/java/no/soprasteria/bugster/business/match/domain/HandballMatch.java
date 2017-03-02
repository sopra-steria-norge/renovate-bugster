package no.soprasteria.bugster.business.match.domain;


import no.soprasteria.bugster.business.team.domain.Team;

import java.util.Objects;

public class HandballMatch implements Match {

    private Integer id;
    private Team homeTeam;
    private Team awayTeam;
    private Score score;

    public HandballMatch(Team homeTeam, Team awayTeam, Score score, String status, String startDate) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.score = score;
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
        throw new UnsupportedOperationException();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        String toString = "[" + homeTeam.getName() + " vs " + awayTeam.getName() + "] ";
        return toString + score.toString();
    }

    @Override
    public int compareTo(Match o) {
        int homeTeamCompare = getHomeTeam().getName().compareTo(o.getHomeTeam().getName());
        if (homeTeamCompare != 0) {
            return homeTeamCompare;
        } else {
            return getAwayTeam().getName().compareTo(getAwayTeam().getName());
        }
    }

}
