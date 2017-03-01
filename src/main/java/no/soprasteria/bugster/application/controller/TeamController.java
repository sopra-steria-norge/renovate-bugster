package no.soprasteria.bugster.application.controller;

import com.google.gson.Gson;
import no.soprasteria.bugster.business.team.domain.Team;
import no.soprasteria.bugster.infrastructure.db.repository.TeamRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

@Path("/teams")
public class TeamController {

    private final Gson gson = new Gson();
    private final TeamRepository teamRepository;

    public TeamController(TeamRepository teamRepository) {
        checkNotNull(teamRepository);
        this.teamRepository = teamRepository;
    }

    @GET
    @Produces("application/json")
    public Response list() {
        List<Team> teams = teamRepository.list();
        return Response.status(200).entity(gson.toJson(teams)).build();
    }

    @GET
    @Produces("application/json")
    @Path("search")
    public Response findByName(@QueryParam("name") String name) {
        Optional<Team> team = teamRepository.findByName(name);
        if(team.isPresent()) {
            return Response.status(200).entity(gson.toJson(team)).build();
        }
        return Response.status(404).build();
    }
}
