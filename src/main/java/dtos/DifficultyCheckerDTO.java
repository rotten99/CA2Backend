package dtos;

import entities.DifficultyChecker;

public class DifficultyCheckerDTO {
    private Long id;
    private String countryName;
    private String flagURL;
    private int timesNotAnswered;


    public DifficultyCheckerDTO(DifficultyChecker dc) {
        this.id = dc.getId();
        this.countryName = dc.getCountryName();
        this.flagURL = dc.getFlagURL();
        this.timesNotAnswered = dc.getTimesNotAnswered();
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getFlagURL() {
        return flagURL;
    }

    public void setFlagURL(String flagURL) {
        this.flagURL = flagURL;
    }

    public int getTimesNotAnswered() {
        return timesNotAnswered;
    }

    public void setTimesNotAnswered(int timesNotAnswered) {
        this.timesNotAnswered = timesNotAnswered;
    }
}
