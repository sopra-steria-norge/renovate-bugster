package no.soprasteria.bugster.business.user.domain;

import java.math.BigDecimal;

public class User {

    private Integer id;
    private String username;
    private BigDecimal balance;


    public User(int id, String username, BigDecimal balance) {
        this.id = id;
        this.username = username;
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
