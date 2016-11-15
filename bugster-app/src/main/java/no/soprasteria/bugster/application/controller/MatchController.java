package no.soprasteria.bugster.application.controller;

import com.google.gson.Gson;
import no.soprasteria.bugster.application.server.AppConfig;
import no.soprasteria.bugster.application.server.EuroAppConfigFile;
import no.soprasteria.bugster.business.match.domain.FootballMatch;
import no.soprasteria.bugster.business.match.domain.Match;
import no.soprasteria.bugster.business.match.domain.MatchStatus;
import no.soprasteria.bugster.business.polling.service.ResultService;
import no.soprasteria.bugster.infrastructure.db.repository.MatchRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/match")
public class MatchController {

    private MatchRepository repository;
    private Gson gson = new Gson();

    public MatchController() {
        AppConfig instance = EuroAppConfigFile.getInstance();
        repository = new MatchRepository(instance.getDatabase());
    }

    @GET
    @Produces("application/json")
    @Path("/all")
    public Response all() {
        List<FootballMatch> list = repository.list();
        return Response.status(200).entity(gson.toJson(list)).build();
    }

    @GET
    @Produces("application/json")
    @Path("/{id}")
    public Response findById(@PathParam("id") int id) {
        Optional<FootballMatch> footballMatch = repository.find(id);
        return Response.status(200).entity(gson.toJson(footballMatch)).build();
    }
}
