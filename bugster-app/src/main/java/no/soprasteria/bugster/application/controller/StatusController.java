package no.soprasteria.bugster.application.controller;

import com.google.gson.Gson;
import no.soprasteria.bugster.business.polling.service.ResultService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/status")
public class StatusController {

    private ResultService service = new ResultService();
    private Gson gson = new Gson();

    @GET
    @Produces("application/json")
    @Path("/all")
    public Response statuser() {
        List<String> allStatuser = service.getAllStatuser();
        return Response.status(200).entity(gson.toJson(allStatuser)).build();
    }
}
