package no.soprasteria.bugster.application.job.transactions;

import no.soprasteria.bugster.application.server.AppConfig;
import no.soprasteria.bugster.business.match.domain.FootballMatch;
import no.soprasteria.bugster.business.match.domain.Match;
import no.soprasteria.bugster.business.polling.service.scraper.NewVgLiveResultsScraper;
import no.soprasteria.bugster.business.polling.service.scraper.ResultsScraper;
import no.soprasteria.bugster.business.team.domain.Team;
import no.soprasteria.bugster.infrastructure.db.repository.MatchRepository;
import no.soprasteria.bugster.infrastructure.db.repository.TeamRepository;
import org.quartz.*;

import java.util.List;
import java.util.Optional;

public class TransactionJob implements Job {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TransactionJob.class);

    public TransactionJob() {
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<Match> poll = null;
        try {
            SchedulerContext schedulerContext = null;
            try {
                schedulerContext = jobExecutionContext.getScheduler().getContext();
            } catch (SchedulerException e1) {
                log.error("Feil med schedulercontext", e1);
            }
            log.info("Henter config.");
            AppConfig config = (AppConfig) schedulerContext.get("config");
            log.info("Oppretter repositories.");
            MatchRepository matchesRepository = new MatchRepository(config.getDatabase());
            TeamRepository teamRepository = new TeamRepository(config.getDatabase());
            log.info("Starter polling");
            ResultsScraper resultsScraper = new NewVgLiveResultsScraper("https://api.vglive.no/v1/vg/events");
            poll = resultsScraper.poll();
            log.info("Fant {} begivenheter", poll.size());
            for (Match match : poll) {
                FootballMatch footballMatch = (FootballMatch) match;
                findOrCreateTeam(footballMatch.getHomeTeam(), teamRepository);
                findOrCreateTeam(footballMatch.getAwayTeam(), teamRepository);
                matchesRepository.insert(footballMatch);
            }
            log.info("Feridg med polling");
        } catch (Exception e) {
            log.error("Kall mot vglive feiler." + (poll == null ?  "Har jeg internett?" :  poll.get(0).hashCode()), e);
        }
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
