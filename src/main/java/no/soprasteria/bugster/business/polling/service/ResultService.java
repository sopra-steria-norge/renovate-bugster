package no.soprasteria.bugster.business.polling.service;

import no.soprasteria.bugster.business.match.domain.FootballMatch;
import no.soprasteria.bugster.business.match.domain.Match;
import no.soprasteria.bugster.business.polling.service.scraper.VgLiveResultsScraper;
import no.soprasteria.bugster.business.polling.service.scraper.ResultsScraper;

import java.util.List;
import java.util.stream.Collectors;

public class ResultService {

    private ResultsScraper resultsScraper = new VgLiveResultsScraper("https://api.vglive.no/v1/vg/events");

    public List<Match> findAll() {
        return resultsScraper.poll();
    }

    public List<Match> findByStatus(String status) {
        List<Match> poll = findAll();
        return poll.stream().filter(match -> ((FootballMatch)match).getStatus().equals(status)).collect(Collectors.toList());
    }

    public List<String> getAllStatuser() {
        List<Match> all = findAll();
        return all.stream().map(match -> ((FootballMatch)match).getStatus()).collect(Collectors.toList());
    }

    void setResultsScraper(ResultsScraper resultsScraper) {
        this.resultsScraper = resultsScraper;
    }
}
