package no.soprasteria.bugster.business.polling.service.scraper.vglive.tournament;

public class TournamentStage {

    private int id;
    private String name;
    private int tournamentId;
    private String tournamentName;
    private int seasonId;
    private String seasonName;
    private int countryId;
    private int sportId;
    private String gender;
    private boolean isCup;
    private String image;

    public TournamentStage(int id, String name, int tournamentId, String tournamentName, int seasonId, String seasonName, int countryId, int sportId, String gender, boolean isCup, String image) {
        this.id = id;
        this.name = name;
        this.tournamentId = tournamentId;
        this.tournamentName = tournamentName;
        this.seasonId = seasonId;
        this.seasonName = seasonName;
        this.countryId = countryId;
        this.sportId = sportId;
        this.gender = gender;
        this.isCup = isCup;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getSportId() {
        return sportId;
    }

    public void setSportId(int sportId) {
        this.sportId = sportId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isCup() {
        return isCup;
    }

    public void setCup(boolean cup) {
        isCup = cup;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
