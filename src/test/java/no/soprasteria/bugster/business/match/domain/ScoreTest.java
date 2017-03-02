package no.soprasteria.bugster.business.match.domain;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ScoreTest {

    @Test
    public void testGetResult() throws Exception {
        assertThat(new Score(1, 0).getResult()).isEqualTo(Result.H);
        assertThat(new Score(1, 1).getResult()).isEqualTo(Result.U);
        assertThat(new Score(1, 2).getResult()).isEqualTo(Result.B);
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

}