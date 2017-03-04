package no.soprasteria.bugster.application.controller;

import com.google.gson.Gson;
import no.soprasteria.bugster.business.bet.domain.Bet;
import no.soprasteria.bugster.infrastructure.db.repository.BetRepository;
import no.soprasteria.bugster.infrastructure.db.repository.OddsRepository;
import no.soprasteria.bugster.infrastructure.db.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.nio.file.attribute.UserPrincipal;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BetsControllerTest {
    private BetRepository betRepository = mock(BetRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);
    private OddsRepository oddsRepository = mock(OddsRepository.class);
    private BetsController betsController = new BetsController(true);
    private Gson gson = new Gson();

    @Before
    public void setup(){
        Whitebox.setInternalState(betsController, "betRepository", betRepository);
        Whitebox.setInternalState(betsController, "userRepository", userRepository);
        Whitebox.setInternalState(betsController, "oddsRepository", oddsRepository);
        Whitebox.setInternalState(betsController, "gson", gson);
    }

    @Test
    public void lists_bets_by_user(){
        String username = "per";
        Bet bet = new Bet();
        List<Bet> bets = Collections.singletonList(bet);
        when(betRepository.listByUser(username)).thenReturn(bets);

        Object expectedResult = Response.ok().entity(gson.toJson(bets)).build().getEntity();
        SecurityContext securityContext = mock(SecurityContext.class);
        UserPrincipal userPrincipal = mock(UserPrincipal.class);
        when(securityContext.getUserPrincipal()).thenReturn(userPrincipal);
        when(userPrincipal.getName()).thenReturn(username);
        Object result = betsController.list(securityContext).getEntity();
        assertTrue(expectedResult.equals(result));
    }

    @Test
    public void places_bet_for_user(){

    }


    @Test
    public void findByName() throws Exception {

    }

    @Test
    public void resultsByName() throws Exception {

    }

}