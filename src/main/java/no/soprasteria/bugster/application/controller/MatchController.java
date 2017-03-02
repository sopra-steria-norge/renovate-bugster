package no.soprasteria.bugster.application.controller;

import com.google.gson.Gson;
import no.soprasteria.bugster.business.match.domain.Match;
import no.soprasteria.bugster.infrastructure.db.repository.MatchRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

import static jersey.repackaged.com.google.common.base.Preconditions.checkNotNull;

@Path("/matches")
public class MatchController {
    private final MatchRepository matchRepository;
    private final Gson gson = new Gson();

    public MatchController(MatchRepository matchRepository) {
        checkNotNull(matchRepository);
        this.matchRepository = matchRepository;
    }

    @GET
    @Produces("application/json")
    public Response all() {
        List<Match> list = matchRepository.list();
        return Response.status(200).entity(gson.toJson(list)).build();
    }

//    @GET
//    @Produces("application/json")
//    @Path("/search/id/{id}")
//    public Response findById(@PathParam("id") int id) {
//        Optional<Match> footballMatch = matchService.findById(id);
//        if(footballMatch.isPresent()) {
//            return Response.status(200).entity(gson.toJson(footballMatch)).build();
//        }
//        return Response.status(400).build();
//    }

//    @GET
//    @Produces("application/json")
//    @Path("/search/name/{id}")
//    public Response findByName(@PathParam("id") String id) {
//        List<Match> byName = matchService.findAllByName(id);
//        return Response.status(200).entity(gson.toJson(byName)).build();
//    }
//
//    @GET
//    @Produces("application/json")
//    @Path("/search/date/{id}")
//    public Response findByDate(@PathParam("id") String date) {
//        List<Match> byName = matchService.findAllByDate(date);
//        return Response.status(200).entity(gson.toJson(byName)).build();
//    }
}
