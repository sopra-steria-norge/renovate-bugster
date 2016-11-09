package no.soprasteria.bugster.business.match.domain;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScoreTest {
    @Test
    public void testGetResult() throws Exception {
        Score score = new Score(1, 2);
        assertEquals(Score.Result.B, score.getResult());
        score = new Score(1, 1);
        assertEquals(Score.Result.U, score.getResult());
        score = new Score(1, 0);
        assertEquals(Score.Result.H, score.getResult());
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