package no.soprasteria.bugster.business.polling.service.scraper.vglive.score;

public class Score {

    private int eventId;
    private int participantId;
    private ParticipantScore score;

    public Score(int eventId, int participantId, ParticipantScore score) {
        this.eventId = eventId;
        this.participantId = participantId;
        this.score = score;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getParticipantId() {
        return participantId;
    }

    public void setParticipantId(int participantId) {
        this.participantId = participantId;
    }

    public ParticipantScore getScore() {
        return score;
    }

    public void setScore(ParticipantScore score) {
        this.score = score;
    }
}
