package no.soprasteria.bugster.business.transaction.service;

import no.soprasteria.bugster.business.transaction.domain.Transaction;
import no.soprasteria.bugster.business.user.domain.User;
import no.soprasteria.bugster.infrastructure.db.repository.RepositoryLocator;
import no.soprasteria.bugster.infrastructure.db.repository.TransactionRepository;
import no.soprasteria.bugster.infrastructure.db.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class TransactionService {

    private TransactionRepository transactionRepository;
    private UserRepository userRepository;

    public TransactionService() {
        transactionRepository = RepositoryLocator.instantiate(TransactionRepository.class);
        userRepository = RepositoryLocator.instantiate(UserRepository.class);
    }

    public List<Transaction> findAllUnhandled() {
        return transactionRepository.list();
    }

    public void expedite(Transaction transaction) {
        Optional<User> byId = userRepository.findById((int) transaction.getUser().getId());

//        userRepository.update(u);
    }

    public void undo(Transaction transaction) {
        
    }
}
