package no.soprasteria.bugster.business.bet.service;

import no.soprasteria.bugster.infrastructure.db.repository.OddsRepository;
import no.soprasteria.bugster.infrastructure.db.repository.RepositoryLocator;

public class BettingService {

    private OddsRepository oddsRepository;

    public BettingService() {
        oddsRepository = RepositoryLocator.instantiate(OddsRepository.class);
    }
}
