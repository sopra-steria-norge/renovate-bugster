package no.soprasteria.bugster.application.scheduling.results;

import no.soprasteria.bugster.business.polling.service.scraper.VgLiveResultsScraper;
import no.soprasteria.bugster.business.match.domain.Match;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

public class ResultPollerJob implements Job {

    public ResultPollerJob() {
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        TeamsRepository teamsRepository = new TeamsRepository(config.getDatabase());
//        MatchesRepository matchesRepository = new MatchesRepository(config.getDatabase());
        VgLiveResultsScraper resultsScraper = new VgLiveResultsScraper("http://old.vglive.no");
        // TODO: Sjekk om det går kamper nå, hvis ikke skal det ikke polles
        List<Match> poll = resultsScraper.poll();
        // TODO: Sjekk om

    }
}
