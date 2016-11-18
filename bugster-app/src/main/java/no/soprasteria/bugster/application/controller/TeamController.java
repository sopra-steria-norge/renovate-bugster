package no.soprasteria.bugster.application.controller;

import com.google.gson.Gson;
import no.soprasteria.bugster.application.server.AppConfig;
import no.soprasteria.bugster.application.server.EuroAppConfigFile;
import no.soprasteria.bugster.business.match.domain.FootballMatch;
import no.soprasteria.bugster.business.team.domain.Team;
import no.soprasteria.bugster.infrastructure.db.repository.MatchRepository;
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
    private TeamRepository teamRepository;
    private MatchRepository matchRepository;

    public TeamController() {
        AppConfig instance = EuroAppConfigFile.getInstance();
        teamRepository = new TeamRepository(instance.getDatabase());
        matchRepository = new MatchRepository(instance.getDatabase());
    }

    @GET
    @Produces("application/json")
    @Path("/all")
    public Response all() {
        List<Team> teams = teamRepository.findAll();
        return Response.status(200).entity(gson.toJson(teams)).build();
    }

    @GET
    @Produces("application/json")
    @Path("/{name}")
    public Response findByName(@PathParam("name") String name) {
        Optional<Team> team = teamRepository.findByName(name);
        return Response.status(200).entity(gson.toJson(team)).build();
    }

    @GET
    @Produces("application/json")
    @Path("/results/{name}")
    public Response resultsByName(@PathParam("name") String name) {
        Optional<Team> team = teamRepository.findByName(name);
        if(team.isPresent()) {
            List<FootballMatch> byName = matchRepository.findByName(team.get().getName());
            Response.status(200).entity(gson.toJson(byName)).build();
        }
        return Response.status(400).build();
    }
}
