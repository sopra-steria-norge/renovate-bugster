package no.soprasteria.bugster.application.controller;

import com.google.gson.Gson;
import no.soprasteria.bugster.business.bet.domain.Odds;
import no.soprasteria.bugster.business.match.domain.Match;
import no.soprasteria.bugster.infrastructure.db.repository.MatchRepository;
import no.soprasteria.bugster.infrastructure.db.repository.OddsRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

import static jersey.repackaged.com.google.common.base.Preconditions.checkNotNull;

@Path("/matches")
public class MatchController {
    private final MatchRepository matchRepository;
    private final OddsRepository oddsRepository;
    private final Gson gson = new Gson();

    public MatchController(){
        this.matchRepository = new MatchRepository();
        this.oddsRepository = new OddsRepository();
    }

    public MatchController(MatchRepository matchRepository, OddsRepository oddsRepository) {
        checkNotNull(matchRepository);
        checkNotNull(oddsRepository);
        this.matchRepository = matchRepository;
        this.oddsRepository =  oddsRepository;
    }

    @GET
    @Produces("application/json")
    public Response all() {
        List<Match> list = matchRepository.list();
        return Response.status(200).entity(gson.toJson(list)).build();
    }

    @GET
    @Produces("application/json")
    @Path("/{id}")
    public Response getMatch(@PathParam("id") int id){
        Match match = matchRepository.findById(id).get();
        List<Odds> odds = oddsRepository.findBy(match.getId());
        match.setOdds(odds);
        return Response.status(200).entity(gson.toJson(match)).build();
    }
}
