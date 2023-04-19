package dtos;

public class CountryDTO {
    private String name;
    private String flagURL;
    private boolean isAnswer;

    public CountryDTO(String name, String flag, boolean isAnswer) {
        this.name = name;
        this.flagURL = flag;
        this.isAnswer = isAnswer;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlagURL() {
        return flagURL;
    }

    public void setFlagURL(String flagURL) {
        this.flagURL = flagURL;
    }

    public boolean isAnswer() {
        return isAnswer;
    }

    public void setAnswer(boolean answer) {
        isAnswer = answer;
    }
}
