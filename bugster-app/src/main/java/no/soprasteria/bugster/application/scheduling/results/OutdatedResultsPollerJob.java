package no.soprasteria.bugster.application.scheduling.results;

import no.soprasteria.bugster.application.server.AppConfig;
import no.soprasteria.bugster.business.polling.service.scraper.NewVgLiveResultsScraper;
import no.soprasteria.bugster.business.match.domain.Match;
import no.soprasteria.bugster.business.polling.service.scraper.ResultsScraper;
import no.soprasteria.bugster.business.team.domain.Team;
import no.soprasteria.bugster.infrastructure.db.repository.MatchRepository;
import no.soprasteria.bugster.infrastructure.db.repository.TeamRepository;
import org.quartz.*;

import java.util.List;
import java.util.Optional;

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
            TeamRepository teamRepository = new TeamRepository(config.getDatabase());

            String randomDate = "2016-" + generateRandomNumberString(1, 13, 2) + "-" + generateRandomNumberString(1, 32, 2);

            ResultsScraper resultsScraper = new NewVgLiveResultsScraper("https://api.vglive.no/v1/vg/events?date=" + randomDate);
            List<Match> poll = resultsScraper.poll();

            for (Match match : poll) {
                Match match = match;
                findOrCreateTeam(footballMatch.getHomeTeam(), teamRepository);
                findOrCreateTeam(footballMatch.getAwayTeam(), teamRepository);
                matchesRepository.insert(footballMatch);
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


    private void findOrCreateTeam(Team team, TeamRepository repository) {
        Optional<Team> teamFromRepo = repository.findByName(team.getName());
        if(!teamFromRepo.isPresent()) {
            repository.insert(team);
            return;
        }
        team.setId(teamFromRepo.get().getId());
    }
}
