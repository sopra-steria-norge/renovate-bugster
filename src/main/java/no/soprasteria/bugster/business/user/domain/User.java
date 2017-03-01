package no.soprasteria.bugster.business.user.domain;

import no.soprasteria.bugster.business.bet.domain.UserBet;
import no.soprasteria.bugster.business.transaction.domain.Transaction;

import java.util.Collections;
import java.util.List;

public class User {

    private Long id;
    private String username;
    private String name;
    private String password;
    private List<Transaction> transactions;
    private List<UserBet> bets;
    private long balance;

    public User(String username, String name, String password) {
        this.id = null;
        this.username = username;
        this.name = name;
        this.password = password;
        this.transactions = Collections.emptyList();
        this.bets = Collections.emptyList();
        this.balance = 0;
    }

    public User(long id, String username, String name, String password, List<Transaction> transactions, List<UserBet> bets, long balance) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.password = password;
        this.transactions = transactions;
        this.bets = bets;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<UserBet> getBets() {
        return bets;
    }

    public void setBets(List<UserBet> bets) {
        this.bets = bets;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
