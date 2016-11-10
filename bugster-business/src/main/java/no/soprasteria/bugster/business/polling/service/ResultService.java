package no.soprasteria.bugster.business.polling.service;

import no.soprasteria.bugster.business.match.domain.FootballMatch;
import no.soprasteria.bugster.business.match.domain.Match;
import no.soprasteria.bugster.business.match.domain.MatchStatus;
import no.soprasteria.bugster.business.polling.service.scraper.ResultsScraper;
import no.soprasteria.bugster.business.polling.service.scraper.VgLiveResultsScraper;

import java.util.List;
import java.util.stream.Collectors;

public class ResultService {

    private ResultsScraper resultsScraper = new VgLiveResultsScraper("http://old.vglive.no");

    public List<Match> findAll() {
        return resultsScraper.poll();
    }

    public List<Match> findByStatus(MatchStatus status) {
        List<Match> poll = resultsScraper.poll();
        return poll.stream().filter(match -> ((FootballMatch)match).getStatus().equals(status)).collect(Collectors.toList());
    }

    void setResultsScraper(ResultsScraper resultsScraper) {
        this.resultsScraper = resultsScraper;
    }
}
