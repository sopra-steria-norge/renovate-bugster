package no.soprasteria.bugster.application.controller;

import com.google.gson.Gson;
import no.soprasteria.bugster.business.match.domain.Match;
import no.soprasteria.bugster.business.match.domain.OldMatchStatus;
import no.soprasteria.bugster.business.polling.service.ResultService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

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
        List<Match> poll = service.findByStatus(OldMatchStatus.ONGOING);
        return Response.status(200).entity(gson.toJson(poll)).build();
    }
}