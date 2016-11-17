package no.soprasteria.bugster.business.match.domain;

public enum OldMatchStatus {
    SCHEDULED("scheduled"),ONGOING("onGoing"),FINISHED("finished");

    private String cssClass;

    OldMatchStatus(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getCssClass() {
        return cssClass;
    }
}
