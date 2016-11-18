package no.soprasteria.bugster.business.match.domain;

public class Score {

    private Integer id;
    private int home;
    private int away;
    private Integer homeExtraTime = null;
    private Integer awayExtraTime = null;
    private Integer homePenalties = null;
    private Integer awayPenalties = null;

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
        if(!isExtraTime() && !isPenalties()) {
            return getResult(home, away);
        } else {
            if(isExtraTime() && !isPenalties()) {
                return getResult(homeExtraTime, awayExtraTime);
            }
            return getResult(homePenalties, awayPenalties);
        }
    }

    private Result getResult(int home, int away) {
        if (home > away) {
            return Result.H;
        } else if (home < away) {
            return Result.B;
        }
        return Result.U;
    }

    @Override
    public String toString() {
        if(!isExtraTime() && !isPenalties()) {
            return "["+home+" - "+away+" ("+getResult()+")]";
        } else if(isExtraTime() && !isPenalties()) {
            return "[" + home + " - " + away + " e.e.o. " + homeExtraTime + " - " + awayExtraTime + " (" + getResult() + ")]";
        } else {
            return "["+home+" - "+away+" e.s.s. "+homePenalties+" - "+awayPenalties+" ("+getResult()+")]";
        }
    }

    public boolean isPenalties() {
        return (awayPenalties != null && homePenalties != null);
    }

    public boolean isExtraTime() {
        return (getAwayExtraTime() != null && getHomeExtraTime() != null);
    }

    public int getAwayPenalties() {
        return awayPenalties;
    }

    public void setAwayPenalties(Integer awayPenalties) {
        this.awayPenalties = awayPenalties;
    }

    public int getHomePenalties() {
        return homePenalties;
    }

    public void setHomePenalties(Integer homePenalties) {
        this.homePenalties = homePenalties;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getAwayExtraTime() {
        return awayExtraTime;
    }

    public void setAwayExtraTime(Integer awayExtraTime) {
        this.awayExtraTime = awayExtraTime;
    }

    public Integer getHomeExtraTime() {
        return homeExtraTime;
    }

    public void setHomeExtraTime(Integer homeExtraTime) {
        this.homeExtraTime = homeExtraTime;
    }

    public enum Result {
        H,U,B
    }
}
