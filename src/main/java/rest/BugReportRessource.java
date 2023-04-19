package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.BugReportDTO;
import dtos.UserDTO;
import entities.BugReport;
import entities.Role;
import entities.User;
import errorhandling.NotFoundException;
import facades.BugReportFacade;
import facades.UserFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.stream.Collectors;


@Path("bugreport")
public class BugReportRessource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final BugReportFacade FACADE =  BugReportFacade.getBugReportFacade(EMF);

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    //This is the endpoint for creating a bugreport
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("create")
    public Response createBugreport(String input){
        BugReportDTO bugReportDTO = GSON.fromJson(input, BugReportDTO.class);
        BugReportDTO bugReportDTONew = FACADE.createBugReport(bugReportDTO);
        return Response.ok().entity(bugReportDTONew).build();
    }

    //This is the endpoint for getting all bugreports
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public Response getAllBugreports(){
        List<BugReportDTO> bugReportDTOList = FACADE.getAllBugReports();
        return Response.ok().entity(bugReportDTOList).build();
    }

    //This is the endpoint for editing a bugreport
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("edit")
    public Response editBugreport(String input) throws NotFoundException {
        BugReportDTO bugReportDTO = GSON.fromJson(input, BugReportDTO.class);
        BugReportDTO bugReportDTONew = FACADE.editBugReport(bugReportDTO);
        return Response.ok().entity(bugReportDTONew).build();
    }



}
