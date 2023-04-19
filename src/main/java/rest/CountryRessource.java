package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.ChuckDTO;
import dtos.CountryDTO;
import dtos.DadDTO;
import dtos.MyJokeDTO;
import facades.CountryFacade;
import facades.Fetch;
import facades.JokeFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("country")
public class CountryRessource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    @Path("answers")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAnswers() throws Exception {
        CountryDTO[] countryDTOs = CountryFacade.getAnswers();
        return Response.ok().entity(countryDTOs).build();

    }
}
