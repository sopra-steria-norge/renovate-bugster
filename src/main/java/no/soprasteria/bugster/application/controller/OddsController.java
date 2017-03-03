package no.soprasteria.bugster.application.controller;

import com.google.gson.Gson;
import jersey.repackaged.com.google.common.base.Preconditions;
import no.soprasteria.bugster.business.bet.domain.Odds;
import no.soprasteria.bugster.business.match.domain.Match;
import no.soprasteria.bugster.infrastructure.db.repository.OddsRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/odds")
public class OddsController {
    private OddsRepository oddsRepository;
    private Gson gson = new Gson();

    public OddsController(){
        this.oddsRepository = new OddsRepository();
    }

    @GET
    @Produces("application/json")
    @Path("/matches/{id}")
    public Response listForMatch(@PathParam("id") Integer matchId) {
        List<Odds> odds = oddsRepository.findBy(matchId);
        return Response.status(200).entity(gson.toJson(odds)).build();
    }
}
