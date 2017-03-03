package no.soprasteria.bugster.application.controller;

import com.google.gson.Gson;
import no.soprasteria.bugster.business.bet.domain.Bet;
import no.soprasteria.bugster.business.user.domain.User;
import no.soprasteria.bugster.infrastructure.db.repository.BetRepository;
import no.soprasteria.bugster.infrastructure.db.repository.RepositoryLocator;
import no.soprasteria.bugster.infrastructure.db.repository.UserRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.math.BigDecimal;
import java.util.List;

@Path("/bets")
public class BetsController {
    private Gson gson = new Gson();
    private BetRepository betRepository;
    private UserRepository userRepository;

    public BetsController() {

        this.betRepository = new BetRepository();
        this.userRepository = RepositoryLocator.instantiate(UserRepository.class);
    }

    @POST
    @Consumes("application/json")
    public void placebet(String json){
        Bet bet = gson.fromJson(json, Bet.class);
        User user = bet.getUser();
        betRepository.insert(bet);
        user.setBalance(user.getBalance().subtract(BigDecimal.valueOf((double)bet.getAmount())));
        userRepository.update(user);
    }

    @GET
    @Produces("application/json")
    public Response list(@Context SecurityContext sc) {
        List<Bet> bets = betRepository.listByUser(sc.getUserPrincipal().getName());
        return Response.status(200).entity(gson.toJson(bets)).build();
    }

}
