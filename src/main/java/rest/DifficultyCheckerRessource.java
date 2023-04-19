package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.*;
import facades.DifficultyCheckerFacade;
import facades.Fetch;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("difficulty")
public class DifficultyCheckerRessource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final DifficultyCheckerFacade FACADE = DifficultyCheckerFacade.getFacade(EMF);

    //This endpoint gets All the Difficulty checkers from the database
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getDifficultyCheckers() throws Exception {
        return Response.ok().entity(GSON.toJson(FACADE.getAllDifficultyCheckers())).build();
    }

    //This endpoint creates a new Difficulty checker
    @Path("create")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public Response createDifficultyChecker(String input) throws Exception {
        DifficultyCheckerDTO df = GSON.fromJson(input, DifficultyCheckerDTO.class);
        DifficultyCheckerDTO dfNew = FACADE.createDifficultyChecker(df);
        return Response.ok().entity(dfNew).build();
    }

    //This endpoint edit a Difficulty checker
    @Path("edit")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    public Response editDifficultyChecker(String input) throws Exception {
        DifficultyCheckerDTO dto = GSON.fromJson(input, DifficultyCheckerDTO.class);
        return Response.ok().entity(FACADE.editDifficultyChecker(dto)).build();
    }

}
