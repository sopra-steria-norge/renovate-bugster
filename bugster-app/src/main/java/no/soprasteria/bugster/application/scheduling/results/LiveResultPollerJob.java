package no.soprasteria.bugster.application.scheduling.results;

import no.soprasteria.bugster.application.server.AppConfig;
import no.soprasteria.bugster.business.match.domain.FootballMatch;
import no.soprasteria.bugster.business.match.domain.Match;
import no.soprasteria.bugster.business.polling.service.scraper.OldVgLiveResultsScraper;
import no.soprasteria.bugster.infrastructure.db.repository.MatchRepository;
import no.soprasteria.bugster.infrastructure.db.repository.TeamRepository;
import org.quartz.*;

import java.util.List;

public class LiveResultPollerJob implements Job {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LiveResultPollerJob.class);

    public LiveResultPollerJob() {
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            SchedulerContext schedulerContext = null;
            try {
                schedulerContext = jobExecutionContext.getScheduler().getContext();
            } catch (SchedulerException e1) {
               log.error("Feil med schedulercontext", e1);
            }
            AppConfig config = (AppConfig) schedulerContext.get("config");

            MatchRepository matchesRepository = new MatchRepository(config.getDatabase());
            TeamRepository teamRepository = new TeamRepository(config.getDatabase());

            OldVgLiveResultsScraper resultsScraper = new OldVgLiveResultsScraper("http://old.vglive.no/");
            List<Match> poll = resultsScraper.poll();

            for (Match match : poll) {
                matchesRepository.insert((FootballMatch) match);
            }
        } catch (Exception e) {
            log.error("Kall mot vglive feiler. Har jeg internett?", e);
        }
    }
}
