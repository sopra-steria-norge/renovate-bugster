package no.soprasteria.bugster.business.match.domain;

import com.google.common.base.Objects;
import jersey.repackaged.com.google.common.base.Preconditions;

public class Score {

    private Integer id;
    private int home;
    private int away;

    public Score(int home, int away) throws IllegalArgumentException {
        Preconditions.checkArgument(home >= 0 && away >= 0);
        this.home = home;
        this.away = away;
    }

    public int getHome() {
        return home;
    }

    public int getAway() {
        return away;
    }

    Result getResult() {
        if (home > away) {
            return Result.H;
        } else if (home < away) {
            return Result.B;
        }
        return Result.U;
    }

    @Override
    public String toString() {
        return "["+home + " - " + away + "(" +getResult()+ ")]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    enum Result {
        H,U,B
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score = (Score) o;
        return home == score.home &&
                away == score.away &&
                Objects.equal(id, score.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, home, away);
    }
}
