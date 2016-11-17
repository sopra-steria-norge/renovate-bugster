package no.soprasteria.bugster.business.polling.service.scraper.vglive.participant;

public class Participant {

    private int id;
    private String type;
    private String name;
    private String gender;
    private int countryId;
    private String image;

    public Participant(int id, String type, String name, String gender, int countryId, String image) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.gender = gender;
        this.countryId = countryId;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
