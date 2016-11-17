package no.soprasteria.bugster.application.scheduling.results;

import no.soprasteria.bugster.application.server.AppConfig;
import no.soprasteria.bugster.business.match.domain.FootballMatch;
import no.soprasteria.bugster.business.polling.service.scraper.OldVgLiveResultsScraper;
import no.soprasteria.bugster.business.match.domain.Match;
import no.soprasteria.bugster.infrastructure.db.repository.MatchRepository;
import org.quartz.*;

import java.util.List;

public class OutdatedResultsPollerJob implements Job {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LiveResultPollerJob.class);

    public OutdatedResultsPollerJob() {
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            SchedulerContext schedulerContext = null;
            try {
                schedulerContext = jobExecutionContext.getScheduler().getContext();
            } catch (SchedulerException e1) {
                e1.printStackTrace();
            }
            AppConfig config = (AppConfig) schedulerContext.get("config");

            MatchRepository matchesRepository = new MatchRepository(config.getDatabase());

            String randomDate = "2016" + generateRandomNumberString(1, 12, 2) + generateRandomNumberString(1, 31, 2);


            OldVgLiveResultsScraper resultsScraper = new OldVgLiveResultsScraper("http://old.vglive.no/#frontpage=" + randomDate);
            List<Match> poll = resultsScraper.poll();

            for (Match match : poll) {
                matchesRepository.insert((FootballMatch) match);
            }
        } catch (Exception e) {
            log.error("Kall mot vglive feiler. Har jeg internett?");
        }
    }

    String generateRandomNumberString(int min, int max, int minNumberOfNumbers) {
        int randomNumber = (int)(min + (Math.random() * max));
        String randomNumberString = "" + randomNumber;
        while (randomNumberString.length() < minNumberOfNumbers) {
            randomNumberString = "0" + randomNumberString;
        }
        return randomNumberString;
    }
}
