package no.soprasteria.bugster.business.match.domain;

import no.soprasteria.bugster.business.team.domain.Team;
import org.apache.commons.lang3.RandomStringUtils;

public class TeamTestData {
    private String name = RandomStringUtils.randomAlphabetic(10);

    public Team build(){
        Team team = new Team(name);
        return team;
    }
}
