package no.soprasteria.bugster.application.controller;

import com.google.gson.Gson;
import no.soprasteria.bugster.business.bet.domain.Bet;
import no.soprasteria.bugster.infrastructure.db.repository.BetRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/bets")
public class BetsController {

    private Gson gson = new Gson();
    private BetRepository betRepository;

    public BetsController() {
        this.betRepository = new BetRepository();
    }

    @POST
    @Consumes("application/json")
    public void placebet(String json){
        Bet bet = gson.fromJson(json, Bet.class);
        betRepository.insert(bet);
    }

    @GET
    @Produces("application/json")
    public Response list() {
        return Response.status(200).build();
//         Bet bet = new Bet(new Odds(1, Result.H, 1.12), Result.H);
//        return Response.status(200).entity(gson.toJson(bet)).build();
    }

//    @GET
//    @Produces("application/json")
//    @Path("/search/name/{name}")
//    public Response findByName(@PathParam("name") String name) {
//        Optional<Team> team = teamService.findByName(name);
//        if(team.isPresent()) {
//            return Response.status(200).entity(gson.toJson(team)).build();
//        }
//        return Response.status(400).build();
//    }
//
//    @GET
//    @Produces("application/json")
//    @Path("/results/{name}")
//    public Response resultsByName(@PathParam("name") String name) {
//        Optional<Team> team = teamService.findByName(name);
//        if(team.isPresent()) {
//            List<Match> byName = matchService.findAllByName(team.get().getName());
//            Response.status(200).entity(gson.toJson(byName)).build();
//        }
//        return Response.status(400).build();
//    }
}
