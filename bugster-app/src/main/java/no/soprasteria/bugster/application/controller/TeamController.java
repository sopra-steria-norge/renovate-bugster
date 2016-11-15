package no.soprasteria.bugster.application.controller;

import com.google.gson.Gson;
import com.sun.jersey.spi.inject.Inject;
import no.soprasteria.bugster.application.server.AppConfig;
import no.soprasteria.bugster.application.server.EuroAppConfigFile;
import no.soprasteria.bugster.business.team.domain.Team;
import no.soprasteria.bugster.infrastructure.db.repository.TeamRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/team")
public class TeamController {

    private Gson gson = new Gson();
    private TeamRepository repository;

    public TeamController() {
        AppConfig instance = EuroAppConfigFile.getInstance();
        repository = new TeamRepository(instance.getDatabase());
    }

    @GET
    @Produces("application/json")
    @Path("/all")
    public Response all() {
        List<Team> teams = repository.findAll();
        return Response.status(200).entity(gson.toJson(teams)).build();
    }

    @GET
    @Produces("application/json")
    @Path("{name}")
    public Response findByName(@PathParam("name") String name) {
        Optional<Team> team = repository.findByName(name);
        return Response.status(200).entity(gson.toJson(team)).build();
    }
}
