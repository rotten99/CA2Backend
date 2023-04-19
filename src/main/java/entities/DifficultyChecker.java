package entities;

import javax.persistence.*;

@Entity
@Table(name = "difficulty_checker")
public class DifficultyChecker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "country_name", nullable = false, length = 50, unique = true)
    private String countryName;

    @Column(name = "flag_url", nullable = false, unique = true)
    private String flagURL;

    @Column(name = "times_not_answered", nullable = false)
    private Integer timesNotAnswered;


    public DifficultyChecker() {
    }

    public DifficultyChecker(String countryName, String flagURL, Integer timesNotAnswered) {
        this.countryName = countryName;
        this.flagURL = flagURL;
        this.timesNotAnswered = timesNotAnswered;
    }

    public Integer getTimesNotAnswered() {
        return timesNotAnswered;
    }

    public void setTimesNotAnswered(Integer timesNotAnswered) {
        this.timesNotAnswered = timesNotAnswered;
    }

    public String getFlagURL() {
        return flagURL;
    }

    public void setFlagURL(String flagURL) {
        this.flagURL = flagURL;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}