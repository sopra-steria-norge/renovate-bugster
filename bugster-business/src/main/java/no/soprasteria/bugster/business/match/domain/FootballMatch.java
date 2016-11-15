package no.soprasteria.bugster.business.match.domain;


import no.soprasteria.bugster.business.team.domain.Team;

public class FootballMatch implements Match {

    private long id;
    private Team homeTeam;
    private Team awayTeam;
    private Score score;
    private MatchStatus status;

    public FootballMatch(Team homeTeam, Team awayTeam, Score score, MatchStatus status) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.status = status;
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
    public void setScore(Score score) {
        this.score = score;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        String toString = "[" + homeTeam.getName() + " vs " + awayTeam.getName() + "] ";
        if(status != MatchStatus.SCHEDULED) {
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
