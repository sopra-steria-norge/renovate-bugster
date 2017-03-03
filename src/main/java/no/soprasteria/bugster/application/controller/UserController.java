package no.soprasteria.bugster.application.controller;

import com.google.gson.Gson;
import no.soprasteria.bugster.application.server.AppConfig;
import no.soprasteria.bugster.application.server.ReloadableAppConfigFile;
import no.soprasteria.bugster.infrastructure.db.repository.RepositoryLocator;
import no.soprasteria.bugster.infrastructure.db.repository.UserRepository;

import javax.servlet.annotation.ServletSecurity;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/users")
public class UserController {

    private Gson gson = new Gson();
    private UserRepository userRepository = RepositoryLocator.instantiate(UserRepository.class);

    public UserController() {
    }

    @GET
    @Produces("application/json")
    public Response getCurrent(@Context SecurityContext sc) {
        return Response.status(200)
                .entity(gson.toJson(userRepository.findByName(sc.getUserPrincipal().getName()).get()))
                .build();
    }
}
