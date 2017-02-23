package no.soprasteria.bugster.business.polling.service.scraper.vglive.event;

public class Status {
    private String type;
    private String subtype;
    private String description;

    public Status(String type, String subtype, String description) {
        this.type = type;
        this.subtype = subtype;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
