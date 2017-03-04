package no.soprasteria.bugster.business.match.domain;

import no.soprasteria.bugster.business.team.domain.Team;

import java.time.LocalDateTime;

public class MatchTestData {
    private Team homeTeam = new TeamTestData().build();
    private Team awayTeam = new TeamTestData().build();
    private Score score = new ScoreTestData().build();

    public Match build(){
        return new FootballMatch(homeTeam, awayTeam, score, "notstarted", LocalDateTime.now());
    }
}
