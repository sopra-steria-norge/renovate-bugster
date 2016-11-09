package no.soprasteria.bugster.business.polling.service.scraper;


import no.soprasteria.bugster.business.match.domain.Match;

import java.util.List;

public abstract class ResultsScraper {

    private String url;

    public ResultsScraper(String url) {
        this.url = url;
    }

    public abstract List<Match> poll();

    public String getUrl() {
        return url;
    }

}
