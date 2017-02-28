package no.soprasteria.bugster.business.transaction.domain;

public enum TransactionType {
    DEPOSIT(Constants.PLUS), WITHDRAWL(Constants.MINUS), WIN(Constants.PLUS), PAY(Constants.MINUS);

    private String action;

    TransactionType(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public boolean isPlus() {
        return this.action.equals(Constants.PLUS);
    }

    public boolean isMinus() {
        return this.action.equals(Constants.MINUS);
    }

    private static class Constants {
        public static final String PLUS = "+";
        public static final String MINUS = "-";
    }
}
