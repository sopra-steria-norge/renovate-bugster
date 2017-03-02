package no.soprasteria.bugster.application.job.results;

import no.soprasteria.bugster.application.server.AppConfig;
import no.soprasteria.bugster.application.server.ReloadableAppConfigFile;
import no.soprasteria.bugster.business.bet.domain.Odds;
import no.soprasteria.bugster.business.match.domain.FootballMatch;
import no.soprasteria.bugster.business.match.domain.Match;
import no.soprasteria.bugster.business.match.domain.Result;
import no.soprasteria.bugster.business.polling.service.scraper.VgLiveResultsScraper;
import no.soprasteria.bugster.business.polling.service.scraper.ResultsScraper;
import no.soprasteria.bugster.infrastructure.db.repository.MatchRepository;
import no.soprasteria.bugster.infrastructure.db.repository.OddsRepository;
import org.quartz.*;

import java.util.List;
import java.util.stream.Collectors;

public class LiveResultPollerJob implements Job {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LiveResultPollerJob.class);
    private MatchRepository matchRepository = new MatchRepository(ReloadableAppConfigFile.getInstance().getDatabase());
    private OddsRepository oddsRepository = new OddsRepository();
    public LiveResultPollerJob() {
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<Match> poll = null;
        try {
            log.info("Henter config.");
            AppConfig config = ReloadableAppConfigFile.getInstance();
            log.info("Oppretter repositories.");
            log.info("Starter polling");
            ResultsScraper resultsScraper = new VgLiveResultsScraper("https://api.vglive.no/v1/vg/events");
            poll = resultsScraper.poll();
            log.info("Fant {} begivenheter", poll.size());
            for (Match match : poll) {
                //TODO Fått rapportert en bug her -- fikse.
                List<Match> existingMatches = matchRepository.list()
                        .stream().filter(m -> m.getHomeTeam().equals(match.getHomeTeam()))
                        .collect(Collectors.toList());
                if (existingMatches.size() > 0) {
                    Match current = existingMatches.get(0);
                    current.updateScore(match.getScore());
                    matchRepository.update(current);
                    updateOdds(current);
                } else {
                    matchRepository.insert(match);
                    updateOdds(match);
                }
            }
            log.info("Ferdig med polling");
        } catch (Exception e) {
            log.error("Kall mot vglive feiler." + (poll == null ? "Har jeg internett?" : poll.get(0).hashCode()), e);
        }
    }

    private void updateOdds(Match match) {
        Odds odds = new Odds(match.getId(), Result.H, calculateOdds());
        oddsRepository.insert(odds);
    }

    private Double calculateOdds() {
        return Math.random()+1;
    }

//    private void findOrCreateTeam(Team team, TeamRepository repository) {
//        Optional<Team> teamFromRepo = repository.findByName(team.getName());
//        if(!teamFromRepo.isPresent()) {
//            repository.insert(team);
//            return;
//        }
//        team.setId(teamFromRepo.get().getId());
//    }
}
