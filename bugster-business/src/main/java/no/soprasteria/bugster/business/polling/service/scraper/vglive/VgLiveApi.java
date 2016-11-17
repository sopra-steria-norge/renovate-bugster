package no.soprasteria.bugster.business.polling.service.scraper.vglive;

import no.soprasteria.bugster.business.polling.service.scraper.vglive.country.Country;
import no.soprasteria.bugster.business.polling.service.scraper.vglive.event.Event;
import no.soprasteria.bugster.business.polling.service.scraper.vglive.participant.Participant;
import no.soprasteria.bugster.business.polling.service.scraper.vglive.score.Score;
import no.soprasteria.bugster.business.polling.service.scraper.vglive.tournament.TournamentStage;

import java.util.List;

public class VgLiveApi {
    private List<Event> events;
    private List<Participant> participants;
    private List<Score> scores;
    private List<TournamentStage> tournamentStages;
    private List<Country> countries;

    public VgLiveApi(List<Event> events, List<Participant> participants, List<Score> scores, List<TournamentStage> tournamentStages, List<Country> countries) {
        this.events = events;
        this.participants = participants;
        this.scores = scores;
        this.tournamentStages = tournamentStages;
        this.countries = countries;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    public List<TournamentStage> getTournamentStages() {
        return tournamentStages;
    }

    public void setTournamentStages(List<TournamentStage> tournamentStages) {
        this.tournamentStages = tournamentStages;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }
}
