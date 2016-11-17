package no.soprasteria.bugster.business.match.domain;

public enum NewMatchStatus {
    SCHEDULED("not-started"),ONGOING("onGoing"),FINISHED("finished");

    private String cssClass;

    NewMatchStatus(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getCssClass() {
        return cssClass;
    }
}
