package no.soprasteria.bugster.business.match.domain;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ScoreTest {

    @Test
    public void testGetResult() throws Exception {
        assertThat(new Score(1, 0).getResult()).isEqualTo(Score.Result.H);
        assertThat(new Score(1, 1).getResult()).isEqualTo(Score.Result.U);
        assertThat(new Score(1, 2).getResult()).isEqualTo(Score.Result.B);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMustBePositiveHomeIntegerAsArguments() throws Exception {
        new Score(-1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMustBePositiveAwayIntegerAsArguments() throws Exception {
        new Score(1, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMustBePositiveIntegerAsArguments() throws Exception {
        new Score(-1, -1);
    }

    @Test
    public void penaltiesOrNot() {
        Score score = new Score(1, 0);
        score.setAwayPenalties(1);
        score.setHomePenalties(5);
        assertThat(score.isPenalties()).isTrue();
        assertThat(new Score(1, 1).isPenalties()).isFalse();
    }
}