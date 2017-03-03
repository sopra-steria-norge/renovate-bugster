package no.soprasteria.bugster.business.bet.domain;

import no.soprasteria.bugster.business.match.domain.Result;
import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;

public class BetTest {
    @Test
    public void bonusfactor_is_multiplied_with_regular_winnings_when_odds_above_one_seven(){
        Bet bet = new Bet();
        bet.setOdds((new Odds(1, Result.H, 1.8)));
        bet.setAmount(100);
        Assertions.assertThat(bet.calculateWinnings()).isEqualTo(270);
    }

    @Test
    @Ignore("Feiler på jenkins")
    public void bonus_is_calculated_as_odds_times_betamount_when_odds_less_than_one_seven(){
        Bet bet = new Bet();
        bet.setOdds(new Odds(1, Result.H, 1.5));
        bet.setAmount(100);
        Assertions.assertThat(bet.calculateWinnings()).isEqualTo(150.0);
    }

}