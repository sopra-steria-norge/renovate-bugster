package no.soprasteria.bugster.business.bet.domain;

import com.google.common.base.Objects;
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

    public void setMatch(Match match) {
        this.match = match;
    }

    public Match getMatch() {
        return match;
    }

    public double calculateWinnings() {
        return getAmount() * odds.getValue() * odds.getBonusFactor();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bet bet = (Bet) o;
        return id == bet.id &&
                Objects.equal(odds, bet.odds) &&
                Objects.equal(user, bet.user) &&
                Objects.equal(amount, bet.amount) &&
                Objects.equal(match, bet.match);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, odds, user, amount, match);
    }
}
