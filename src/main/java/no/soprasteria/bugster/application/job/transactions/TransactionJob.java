package no.soprasteria.bugster.application.job.transactions;

import no.soprasteria.bugster.application.server.AppConfig;
import no.soprasteria.bugster.application.server.ReloadableAppConfigFile;
import no.soprasteria.bugster.business.match.domain.Match;
import no.soprasteria.bugster.business.transaction.service.TransactionService;
import org.quartz.*;

import java.util.List;

public class TransactionJob implements Job {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TransactionJob.class);

    private TransactionService service = new TransactionService();

    public TransactionJob() {
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<Match> poll = null;
        try {
            log.info("Henter config.");
            AppConfig config = ReloadableAppConfigFile.getInstance();
            log.info("Oppretter repositories.");
//            MatchRepository matchesRepository = new MatchRepository(config.getDatabase());
//            TeamRepository teamRepository = new TeamRepository(config.getDatabase());
            log.info("Starter utbetaling");

            log.info("Fant {} begivenheter", poll.size());
            log.info("Feridg med polling");
        } catch (Exception e) {
            log.error("Kall mot vglive feiler." + (poll == null ?  "Har jeg internett?" :  poll.get(0).hashCode()), e);
        }
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
