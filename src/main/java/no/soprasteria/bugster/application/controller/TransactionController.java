package no.soprasteria.bugster.application.controller;

import com.google.gson.Gson;
import no.soprasteria.bugster.application.server.AppConfig;
import no.soprasteria.bugster.application.server.ReloadableAppConfigFile;
import no.soprasteria.bugster.business.match.domain.Match;
import no.soprasteria.bugster.business.match.service.MatchService;
import no.soprasteria.bugster.business.team.domain.Team;
import no.soprasteria.bugster.business.team.service.TeamService;
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
public class TransactionController {

    private Gson gson = new Gson();
    private TeamService teamService;
    private MatchService matchService;

    public TransactionController() {
        AppConfig instance = ReloadableAppConfigFile.getInstance();
//        teamService = new TeamService(new TeamRepository(instance.getDatabase()));
//        matchService = new MatchService(new MatchRepository(instance.getDatabase()));
    }

    @GET
    @Produces("application/json")
    @Path("/all")
    public Response all() {
        List<Team> teams = teamService.findAll();
        return Response.status(200).entity(gson.toJson(teams)).build();
    }

    @GET
    @Produces("application/json")
    @Path("/search/name/{name}")
    public Response findByName(@PathParam("name") String name) {
        Optional<Team> team = teamService.findByName(name);
        if(team.isPresent()) {
            return Response.status(200).entity(gson.toJson(team)).build();
        }
        return Response.status(400).build();
    }

    @GET
    @Produces("application/json")
    @Path("/results/{name}")
    public Response resultsByName(@PathParam("name") String name) {
        Optional<Team> team = teamService.findByName(name);
        if(team.isPresent()) {
            List<Match> byName = matchService.findAllByName(team.get().getName());
            Response.status(200).entity(gson.toJson(byName)).build();
        }
        return Response.status(400).build();
    }
}
