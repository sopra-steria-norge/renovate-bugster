package no.soprasteria.bugster.application.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import no.soprasteria.bugster.application.controller.dto.Matches;
import no.soprasteria.bugster.application.controller.json.LocalDateTimeSerializer;
import no.soprasteria.bugster.business.match.service.MatchService;
import no.soprasteria.bugster.infrastructure.db.repository.MatchRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;

@Path("/index")
public class IndexController {

    private Gson gson;
    private MatchService matchService;

    public IndexController() {
        matchService = new MatchService(new MatchRepository());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        gson = gsonBuilder.create();
    }

    @GET
    @Produces("application/json")
    @Path("/")
    public Response all() {
        Matches matches = new Matches(matchService.findAll());
        return Response.status(200).entity(gson.toJson(matches)).build();
    }

}
