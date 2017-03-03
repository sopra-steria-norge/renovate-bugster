package no.soprasteria.bugster.business.bet.domain;

import no.soprasteria.bugster.business.match.domain.Result;

import java.time.LocalDateTime;

public class Odds {
    private Integer id;
    private Double value;
    private Integer matchId;
    private Result result;
    private Double bonusFactor;

    public Odds(Integer matchId, Result result, Double value){
        this.matchId = matchId;
        this.result = result;
        this.value = value;
        initBonusFactor(value);
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

    public double getBonusFactor(){
        return bonusFactor;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public Result getResult() {
        return result;
    }

    public LocalDateTime getTimestampedAt() {
        return LocalDateTime.now();
    }

    private void initBonusFactor(Double value) {
        if(value > BonusFactorBoundary.VALUE){
            bonusFactor = 1.5;
        }
    }
}
