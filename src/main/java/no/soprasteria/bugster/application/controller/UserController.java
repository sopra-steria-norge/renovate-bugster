package no.soprasteria.bugster.application.controller;

import com.google.gson.Gson;
import no.soprasteria.bugster.application.server.AppConfig;
import no.soprasteria.bugster.application.server.ReloadableAppConfigFile;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/user")
public class UserController {

    private Gson gson = new Gson();

    public UserController() {
        AppConfig instance = ReloadableAppConfigFile.getInstance();
//        teamService = new UserService(new TeamRepository(instance.getDatabase()));
    }

    @GET
    @Produces("application/json")
    @Path("/all")
    public Response all() {
        return null;
    }
//        List<Team> teams = teamService.findAll();
//        return Response.status(200).entity(gson.toJson(teams)).build();
//    }

    @GET
    @Produces("application/json")
    @Path("/search/name/{name}")
    public Response findByName(@PathParam("name") String name) {
//        Optional<Team> team = teamService.findByName(name);
//        if(team.isPresent()) {
//            return Response.status(200).entity(gson.toJson(team)).build();
//        }
        return Response.status(400).build();
    }
}
