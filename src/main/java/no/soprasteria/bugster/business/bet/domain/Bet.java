package no.soprasteria.bugster.business.bet.domain;

import no.soprasteria.bugster.business.match.domain.Match;
import no.soprasteria.bugster.business.user.domain.User;

public class Bet {
    private int id;
    private Odds odds;
    private User user;
    private Integer amount;
    private Match match;

    public Odds getOdds() {
        return odds;
    }

    public void setOdds(Odds odds) {
        this.odds = odds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
