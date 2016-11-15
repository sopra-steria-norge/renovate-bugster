package no.soprasteria.bugster.application.controller;

import com.google.gson.Gson;
import no.soprasteria.bugster.application.server.AppConfig;
import no.soprasteria.bugster.application.server.EuroAppConfigFile;
import no.soprasteria.bugster.business.match.domain.FootballMatch;
import no.soprasteria.bugster.business.match.domain.Match;
import no.soprasteria.bugster.business.match.domain.MatchStatus;
import no.soprasteria.bugster.business.polling.service.ResultService;
import no.soprasteria.bugster.business.polling.service.scraper.ResultsScraper;
import no.soprasteria.bugster.business.polling.service.scraper.VgLiveResultsScraper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/result")
public class VgliveNowResultController {

    private ResultService service = new ResultService();
    private Gson gson = new Gson();

    @GET
    @Produces("application/json")
    @Path("/all")
    public Response all() {
        List<Match> poll = service.findAll();
        return Response.status(200).entity(gson.toJson(poll)).build();
    }

    @GET
    @Produces("application/json")
    @Path("/ongoing")
    public Response ongoing() {
        List<Match> poll = service.findByStatus(MatchStatus.ONGOING);
        return Response.status(200).entity(gson.toJson(poll)).build();
    }
}
