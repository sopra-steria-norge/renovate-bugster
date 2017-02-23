package no.soprasteria.bugster.business.polling.service.scraper.vglive.score;

public class ParticipantScore {

    private int ordinaryTime;
    private Integer extraTime;
    private Integer penaltyShootout;

    public ParticipantScore(int ordinaryTime, Integer extraTime, Integer penaltyShootout) {
        this.ordinaryTime = ordinaryTime;
        this.extraTime = extraTime;
        this.penaltyShootout = penaltyShootout;
    }

    public int getOrdinaryTime() {
        return ordinaryTime;
    }

    public void setOrdinaryTime(int ordinaryTime) {
        this.ordinaryTime = ordinaryTime;
    }

    public Integer getExtraTime() {
        return extraTime;
    }

    public void setExtraTime(Integer extraTime) {
        this.extraTime = extraTime;
    }

    public Integer getPenaltyShootout() {
        return penaltyShootout;
    }

    public void setPenaltyShootout(Integer penaltyShootout) {
        this.penaltyShootout = penaltyShootout;
    }
}
