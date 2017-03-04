package no.soprasteria.bugster.business.bet.service;

import no.soprasteria.bugster.business.bet.domain.Bet;
import no.soprasteria.bugster.infrastructure.db.repository.BetRepository;
import no.soprasteria.bugster.infrastructure.db.repository.OddsRepository;
import no.soprasteria.bugster.infrastructure.db.repository.RepositoryLocator;

public class BettingService {

    private OddsRepository oddsRepository;
    private BetRepository betRepository;

    public BettingService() {
        oddsRepository = RepositoryLocator.instantiate(OddsRepository.class);
    }

    public void placeBet(Bet bet) {
//        transactionRepository.with
    }
}
