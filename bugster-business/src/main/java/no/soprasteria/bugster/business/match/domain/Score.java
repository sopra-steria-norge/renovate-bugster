package no.soprasteria.bugster.business.match.domain;

public class Score {

    private int home;
    private int away;
    private int homePenalties;
    private int awayPenalties;
    private boolean extraTime;
    private boolean penalties;

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
        return penalties;
    }

    public void setPenalties(boolean penalties) {
        penalties = penalties;
    }

    public boolean isExtraTime() {
        return extraTime;
    }

    public void setExtraTime(boolean extraTime) {
        this.extraTime = extraTime;
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

    public enum Result {
        H,U,B
    }
}
