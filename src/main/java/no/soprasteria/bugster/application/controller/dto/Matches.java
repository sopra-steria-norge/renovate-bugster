package no.soprasteria.bugster.application.controller.dto;

import no.soprasteria.bugster.business.match.domain.Match;

import java.util.List;

public class Matches {
    private List<Match> matches;

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public Matches(List<Match> matches) {

        this.matches = matches;
    }
}
