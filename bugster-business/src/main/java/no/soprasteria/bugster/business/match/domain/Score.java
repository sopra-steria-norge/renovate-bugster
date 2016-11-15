package no.soprasteria.bugster.business.match.domain;

public class Score {

    private int id;
    private int home;
    private int away;
    private Integer homePenalties;
    private Integer awayPenalties;

    public Score(int home, int away) throws IllegalArgumentException {
        if(home < 0 || away < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            if(home < 0) {
                stringBuilder.append("Home must be a positive integer");
            }
            if(away < 0) {
                stringBuilder.append("Away must be a positive integer");
            }

            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.home = home;
        this.away = away;
    }

    public int getHome() {
        return home;
    }

    public int getAway() {
        return away;
    }

    public Result getResult() {
        if(home > away) {
            return Result.H;
        } else if(home < away) {
            return Result.B;
        }
        return Result.U;
    }

    @Override
    public String toString() {
        return "["+home+" - "+away+" ("+getResult()+")]";
    }

    public boolean isPenalties() {
        return (awayPenalties != null && homePenalties != null);
    }

    public int getAwayPenalties() {
        return awayPenalties;
    }

    public void setAwayPenalties(int awayPenalties) {
        this.awayPenalties = awayPenalties;
    }

    public int getHomePenalties() {
        return homePenalties;
    }

    public void setHomePenalties(int homePenalties) {
        this.homePenalties = homePenalties;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public enum Result {
        H,U,B
    }
}
