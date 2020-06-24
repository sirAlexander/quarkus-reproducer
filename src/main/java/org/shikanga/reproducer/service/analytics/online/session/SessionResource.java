package org.shikanga.reproducer.service.analytics.online.session;


import io.smallrye.mutiny.Uni;
import org.shikanga.reproducer.service.analytics.online.session.service.AnalyticsSessionService;
import org.shikanga.reproducer.service.analytics.online.session.service.ToSessionTransformer;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/sessions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SessionResource {

    @Inject
    AnalyticsSessionService analyticsSessionService;

    @GET
    @Path("{sessionId}")
    public Uni<Response> getSession(@PathParam("sessionId") String sessionId){
        return analyticsSessionService.findBySessionId(sessionId)
                .map(ToSessionTransformer::transform)
                .map(s -> Response.status(Response.Status.OK).entity(s).build());
    }
}
