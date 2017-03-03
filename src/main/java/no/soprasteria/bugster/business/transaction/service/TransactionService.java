package no.soprasteria.bugster.business.transaction.service;

import no.soprasteria.bugster.business.transaction.domain.Transaction;
import no.soprasteria.bugster.business.transaction.domain.TransactionStatus;
import no.soprasteria.bugster.business.user.domain.User;
import no.soprasteria.bugster.infrastructure.db.repository.RepositoryLocator;
import no.soprasteria.bugster.infrastructure.db.repository.TransactionRepository;
import no.soprasteria.bugster.infrastructure.db.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransactionService {

    private TransactionRepository transactionRepository;

    public TransactionService() {
        transactionRepository = RepositoryLocator.instantiate(TransactionRepository.class);
    }

    public List<Transaction> findAllUnhandled() {
        return transactionRepository.list();
    }

    public void expedite(Transaction transaction) {
        if (transaction.getType().isPlus()) {
            transaction.getUser().setBalance(transaction.getUser().getBalance().add(transaction.getValue()));
        } else {
            transaction.getUser().setBalance(transaction.getUser().getBalance().subtract(transaction.getValue()));
        }

        transaction.setStatus(TransactionStatus.EXPEDITED);
        transactionRepository.update(transaction);
    }

    public void undo(Transaction transaction) {
        if (transaction.getType().isPlus()) {
            transaction.getUser().setBalance(transaction.getUser().getBalance().subtract(transaction.getValue()));
        } else {
            transaction.getUser().setBalance(transaction.getUser().getBalance().add(transaction.getValue()));
        }

        transaction.setStatus(TransactionStatus.EXPEDITED);
        transactionRepository.update(transaction);
    }
}
