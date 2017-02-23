package no.soprasteria.bugster.business.polling.service.scraper;


import no.soprasteria.bugster.business.match.domain.Match;

import java.util.List;

public abstract class ResultsScraper {

    private String url;

    ResultsScraper(String url) {
        this.url = url;
    }

    public abstract List<Match> poll();

    String getUrl() {
        return url;
    }

}
