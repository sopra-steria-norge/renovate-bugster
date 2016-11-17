package no.soprasteria.bugster.business.polling.service.scraper.vglive.event;

public class Event {
    private int id;
    private int[] participants;
    private String startDate;
    private Status status;
    private Phase currentPhase;
    private String round;
    private int tournamentStageId;
    private Properties properties;

    public Event(int id, int[] participants, String startDate, Status status, Phase currentPhase, String round, int tournamentStageId, Properties properties) {
        this.id = id;
        this.participants = participants;
        this.startDate = startDate;
        this.status = status;
        this.currentPhase = currentPhase;
        this.round = round;
        this.tournamentStageId = tournamentStageId;
        this.properties = properties;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int[] getParticipants() {
        return participants;
    }

    public void setParticipants(int[] participants) {
        this.participants = participants;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Phase getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(Phase currentPhase) {
        this.currentPhase = currentPhase;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public int getTournamentStageId() {
        return tournamentStageId;
    }

    public void setTournamentStageId(int tournamentStageId) {
        this.tournamentStageId = tournamentStageId;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
