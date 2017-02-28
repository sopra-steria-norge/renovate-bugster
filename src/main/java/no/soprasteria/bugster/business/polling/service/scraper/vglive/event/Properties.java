package no.soprasteria.bugster.business.polling.service.scraper.vglive.event;

public class Properties {
    private boolean lineups;
    private boolean standings;
    private String coverageStatus;
    private String autoReportType;

    public Properties(boolean lineups, boolean standings, String coverageStatus, String autoReportType) {
        this.lineups = lineups;
        this.standings = standings;
        this.coverageStatus = coverageStatus;
        this.autoReportType = autoReportType;
    }

    public boolean isLineups() {
        return lineups;
    }

    public void setLineups(boolean lineups) {
        this.lineups = lineups;
    }

    public boolean isStandings() {
        return standings;
    }

    public void setStandings(boolean standings) {
        this.standings = standings;
    }

    public String getCoverageStatus() {
        return coverageStatus;
    }

    public void setCoverageStatus(String coverageStatus) {
        this.coverageStatus = coverageStatus;
    }

    public String getAutoReportType() {
        return autoReportType;
    }

    public void setAutoReportType(String autoReportType) {
        this.autoReportType = autoReportType;
    }

}
