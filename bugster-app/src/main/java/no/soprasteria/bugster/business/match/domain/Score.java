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

    Result getResult() {
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

    boolean isPenalties() {
        return (awayPenalties != null && homePenalties != null);
    }

    private boolean isExtraTime() {
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

    public boolean isProbablyHandBallMatch() {
        return home > 10 || away > 10;
    }

    enum Result {
        H,U,B
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Score score = (Score) o;

        if (home != score.home) return false;
        if (away != score.away) return false;
        if (id != null ? !id.equals(score.id) : score.id != null) return false;
        if (homeExtraTime != null ? !homeExtraTime.equals(score.homeExtraTime) : score.homeExtraTime != null)
            return false;
        if (awayExtraTime != null ? !awayExtraTime.equals(score.awayExtraTime) : score.awayExtraTime != null)
            return false;
        if (getHomePenalties() != (score.getHomePenalties()))
            return false;
        return getAwayPenalties() == (score.getAwayPenalties());
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + home;
        result = 31 * result + away;
        result = 31 * result + (homeExtraTime != null ? homeExtraTime.hashCode() : 0);
        result = 31 * result + (awayExtraTime != null ? awayExtraTime.hashCode() : 0);
        result = 31 * result + (Integer.valueOf(getHomePenalties()).hashCode());
        result = 31 * result + (Integer.valueOf(getAwayPenalties()).hashCode());
        return result;
    }
}
