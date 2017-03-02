package no.soprasteria.bugster.business.bet.domain;

import no.soprasteria.bugster.business.match.domain.Result;

public class Odds {
    private Integer id;
    private Double value;
    private Integer matchId;
    private Result result;

    public Odds(Integer matchId, Result result, Double value){
        this.matchId = matchId;
        this.result = result;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public Result getResult() {
        return result;
    }
}
