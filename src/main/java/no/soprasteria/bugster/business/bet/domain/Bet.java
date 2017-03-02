package no.soprasteria.bugster.business.bet.domain;

import no.soprasteria.bugster.business.match.domain.Result;

public class Bet {
    private Odds odds;
    private Result bettedResult;
    private int id;

    public Bet(Odds odds, Result bettedResult){
        this.odds = odds;
        this.bettedResult = bettedResult;
    }

    public Odds getOdds() {
        return odds;
    }

    public Result getBettedResult() {
        return bettedResult;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
