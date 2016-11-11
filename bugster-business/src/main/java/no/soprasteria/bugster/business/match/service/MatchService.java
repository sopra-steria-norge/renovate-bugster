package no.soprasteria.bugster.business.match.service;

import no.soprasteria.bugster.business.match.domain.Match;
import no.soprasteria.bugster.business.polling.service.ResultService;

import java.util.Collections;
import java.util.List;

public class MatchService {

    private ResultService resultService = new ResultService();

    public List<Match> findAll() {
        return resultService.findAll();
    }

    public List<Match> findSortedMatches() {
        List<Match> all = findAll();
        Collections.sort(all);
        return all;
    }

}
