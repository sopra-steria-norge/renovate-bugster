package no.soprasteria.bugster.business.polling.service.scraper.vglive.event;

public class Phase {
    private String type;
    private String progressStatus;
    private String startTime;
    private int timeOffset;

    public Phase(String type, String progressStatus, String startTime, int timeOffset) {
        this.type = type;
        this.progressStatus = progressStatus;
        this.startTime = startTime;
        this.timeOffset = timeOffset;
    }

    public String getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(String progressStatus) {
        this.progressStatus = progressStatus;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getTimeOffset() {
        return timeOffset;
    }

    public void setTimeOffset(int timeOffset) {
        this.timeOffset = timeOffset;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
