package no.soprasteria.bugster.business.user.domain;

import no.soprasteria.bugster.business.bet.domain.UserBet;
import no.soprasteria.bugster.business.transaction.domain.Transaction;

import java.util.List;

public class User {

    private String username;
    private List<Transaction> transactions;
    private List<UserBet> bets;
}
