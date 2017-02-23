package no.soprasteria.bugster.business.match.domain;

public enum MatchStatus {
    SCHEDULED("scheduled"),ONGOING("onGoing"),FINISHED("finished");

    private String cssClass;

    MatchStatus(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getCssClass() {
        return cssClass;
    }
}
