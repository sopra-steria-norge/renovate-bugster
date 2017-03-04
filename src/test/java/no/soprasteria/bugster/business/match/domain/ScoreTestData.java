package no.soprasteria.bugster.business.match.domain;

import java.util.Random;

public class ScoreTestData {
    private int home = new Random().nextInt(1);
    private int away = new Random().nextInt(1);

    public Score build(){
        return new Score(home, away);
    }
}
