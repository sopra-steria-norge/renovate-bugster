package no.soprasteria.bugster.business.bet.service;

import no.soprasteria.bugster.business.bet.domain.Bet;
import no.soprasteria.bugster.business.transaction.domain.Transaction;
import no.soprasteria.bugster.infrastructure.db.repository.BetRepository;
import no.soprasteria.bugster.infrastructure.db.repository.OddsRepository;
import no.soprasteria.bugster.infrastructure.db.repository.RepositoryLocator;
import no.soprasteria.bugster.infrastructure.db.repository.TransactionRepository;

public class BettingService {

    private OddsRepository oddsRepository;
    private BetRepository betRepository;
    private TransactionRepository transactionRepository;

    public BettingService() {
        oddsRepository = RepositoryLocator.instantiate(OddsRepository.class);
    }

    public void placeBet(Bet bet){
//        transactionRepository.with
    }
}
