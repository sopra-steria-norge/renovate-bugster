package no.soprasteria.bugster.business.polling.service;

import no.soprasteria.bugster.business.match.domain.FootballMatch;
import no.soprasteria.bugster.business.match.domain.Match;
import no.soprasteria.bugster.business.match.domain.OldMatchStatus;
import no.soprasteria.bugster.business.polling.service.scraper.NewVgLiveResultsScraper;
import no.soprasteria.bugster.business.polling.service.scraper.ResultsScraper;
import no.soprasteria.bugster.business.polling.service.scraper.OldVgLiveResultsScraper;

import java.util.List;
import java.util.stream.Collectors;

public class ResultService {

    private ResultsScraper resultsScraper = new NewVgLiveResultsScraper("http://vglive.no");

    public List<Match> findAll() {
        return resultsScraper.poll();
    }

    public List<Match> findByStatus(String status) {
        List<Match> poll = findAll();
        return poll.stream().filter(match -> ((FootballMatch)match).getStatus().equals(status)).collect(Collectors.toList());
    }

    void setResultsScraper(ResultsScraper resultsScraper) {
        this.resultsScraper = resultsScraper;
    }
}
