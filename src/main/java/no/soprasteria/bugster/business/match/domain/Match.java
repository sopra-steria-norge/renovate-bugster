package no.soprasteria.bugster.business.match.domain;

import no.soprasteria.bugster.business.bet.domain.Odds;
import no.soprasteria.bugster.business.team.domain.Team;

import java.util.List;

public interface Match extends Comparable<Match> {

    void setId(Integer id);

    Integer getId();

    Team getHomeTeam();

    void setHomeTeam(Team homeTeam);

    Team getAwayTeam();

    void setAwayTeam(Team awayTeam);

    Score getScore();

    void updateScore(Score score);

    void setOdds(List<Odds> odds);
}
