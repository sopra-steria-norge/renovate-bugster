package no.soprasteria.bugster.application.job.results;

import no.soprasteria.bugster.business.bet.domain.Odds;
import no.soprasteria.bugster.business.match.domain.FootballMatch;
import no.soprasteria.bugster.business.match.domain.Match;
import no.soprasteria.bugster.business.match.domain.Result;
import no.soprasteria.bugster.business.polling.service.scraper.VgLiveResultsScraper;
import no.soprasteria.bugster.business.polling.service.scraper.ResultsScraper;
import no.soprasteria.bugster.infrastructure.db.repository.MatchRepository;
import no.soprasteria.bugster.infrastructure.db.repository.OddsRepository;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import no.soprasteria.bugster.infrastructure.db.repository.RepositoryLocator;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class LiveResultPollerJob implements Job {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LiveResultPollerJob.class);
    private MatchRepository matchRepository = RepositoryLocator.instantiate(MatchRepository.class);
    private OddsRepository oddsRepository = new OddsRepository();

    public LiveResultPollerJob() {
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<Match> poll = null;
        try {
            log.info("Starter polling");
            ResultsScraper resultsScraper = new VgLiveResultsScraper("https://api.vglive.no/v1/vg/events");
            poll = resultsScraper.poll();
            log.info("Fant {} begivenheter", poll.size());
            for (Match match : poll) {
                List<Match> existingMatches = matchRepository.list()
                        .stream().filter(m -> m.getHomeTeam().equals(match.getHomeTeam()))
                        .collect(Collectors.toList());
                if (existingMatches.size() > 0) {
                    Match current = existingMatches.get(0);
                    current.updateScore(match.getScore());
                    matchRepository.update(current);
                    updateOdds(current);
                    if(hasChangedStatusToEnded(current, match)) {
                        payoutToWinners(match);
                    }
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

    private boolean hasChangedStatusToEnded(Match current, Match match) {
        FootballMatch footballMatch = (FootballMatch) match;
        FootballMatch currentFootballMatch = (FootballMatch) current;
        return !footballMatch.getStatus().equals(currentFootballMatch.getStatus()) && footballMatch.getStatus().equals("finished");
    }

    private void payoutToWinners(Match match) {

    }

    private void updateOdds(Match match) {
        Odds home = new Odds(match.getId(), Result.H, calculateOdds());
        Odds away = new Odds(match.getId(), Result.B, calculateOdds());
        Odds draw = new Odds(match.getId(), Result.U, calculateOdds());
        oddsRepository.insert(home);
        oddsRepository.insert(away);
        oddsRepository.insert(draw);
    }

    private Double calculateOdds() {
        return new BigDecimal(Math.random()+1).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

}
