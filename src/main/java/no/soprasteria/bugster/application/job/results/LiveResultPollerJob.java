package no.soprasteria.bugster.application.job.results;

import no.soprasteria.bugster.business.match.domain.FootballMatch;
import no.soprasteria.bugster.business.match.domain.Match;
import no.soprasteria.bugster.business.polling.service.scraper.ResultsScraper;
import no.soprasteria.bugster.business.polling.service.scraper.VgLiveResultsScraper;
import no.soprasteria.bugster.infrastructure.db.repository.MatchRepository;
import no.soprasteria.bugster.infrastructure.db.repository.RepositoryLocator;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

public class LiveResultPollerJob implements Job {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LiveResultPollerJob.class);

    public LiveResultPollerJob() {
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<Match> poll = null;
        try {
            log.info("Oppretter repositories.");
            MatchRepository matchesRepository = RepositoryLocator.instantiate(MatchRepository.class);
            log.info("Starter polling");
            ResultsScraper resultsScraper = new VgLiveResultsScraper("https://api.vglive.no/v1/vg/events");
            poll = resultsScraper.poll();
            log.info("Fant {} begivenheter", poll.size());
            for (Match match : poll) {
                FootballMatch footballMatch = (FootballMatch) match;
                matchesRepository.insert(footballMatch);
            }
            log.info("Feridg med polling");
        } catch (Exception e) {
            log.error("Kall mot vglive feiler." + (poll == null ? "Har jeg internett?" : poll.get(0).hashCode()), e);
        }
    }
}
