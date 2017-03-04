package no.soprasteria.bugster.business.transaction.domain;

import no.soprasteria.bugster.business.user.domain.User;

import java.math.BigDecimal;
//TODOO
public class Transaction {
    private User user;
    private TransactionType type;
    private boolean revoked;
    private TransactionStatus status;
    private BigDecimal value;

    public Transaction(User user, TransactionType type, boolean revoked, TransactionStatus status, BigDecimal value) {
        this.user = user;
        this.type = type;
        this.revoked = revoked;
        this.status = status;
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public TransactionType getType() {
        return type;
    }

    public BigDecimal getValue() {
        return value;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }
}
