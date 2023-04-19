package facades;
import dtos.CountryDTO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.util.Random;

public class CountryFacade {


    private static CountryFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private CountryFacade() {}


    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static CountryFacade getCountryFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CountryFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public static CountryDTO[] getAnswers() throws IOException {
        CountryDTO[] answers = new CountryDTO[4];
        String apiUrl = "https://restcountries.com/v3.1/all?fields=name,flags";
        String json = Fetch.fetchData(apiUrl);
        String[] jsonArr = json.split("}}}},");


        for (int i = 0; i < answers.length; i++) {
            int random = generateRandomNumber(jsonArr.length);
            String countryName = getCountryName(jsonArr[random].split("\"},\"")[1]);
            String flagURL = getFlagURL(jsonArr[random].split("\"},\"")[0]);
            answers[i] = new CountryDTO(countryName, flagURL, false);
        }

        // Set one of the answers to be the correct answer
        int correctAnswer = generateRandomNumber(answers.length);
        answers[correctAnswer].setAnswer(true);
        return answers;
    }



    //This method generates a random number above 0 and below the max value
    private static int generateRandomNumber(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }


    //This method fetches the flags png from the API
    private static String getFlagURL(String flagURL) throws IOException {

        flagURL = flagURL.split("\"")[5];

        return flagURL;
    }

    //This method get only the common name of the country
    private static String getCountryName(String country) {
        String[] countryArr = country.split(",");
        countryArr = countryArr[0].split(":");
        String name = countryArr[2].replace("\"", "");
        return name;
    }



}
