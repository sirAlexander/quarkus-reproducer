package org.shikanga.reproducer.service.analytics.online.user;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.shikanga.reproducer.service.analytics.online.user.domain.User;
import org.shikanga.reproducer.service.analytics.online.user.service.AnalyticsUserService;
import org.shikanga.reproducer.service.analytics.online.user.service.ToUserTransformer;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    AnalyticsUserService analyticsUserService;

    @GET
    @Counted(name = "getAllAnalyticsUsers", description = "How many times have we retrieved all analytics users.")
    @Timed(name = "allAnalyticsUsersTimer",
            description = "A measure how long it takes to retrieve all analytics users.",
            unit = MetricUnits.MILLISECONDS)
    public Multi<User> getAll() {
        return analyticsUserService.findAllAsync()
                .map(ToUserTransformer::transform);
    }

    @GET
    @Path("{userId}")
    public Uni<Response> getUser(@PathParam("userId") String userId) {
        return analyticsUserService.findByUserId(userId)
                .map(ToUserTransformer::transform)
                .map(u -> Response.status(Response.Status.OK).entity(u).build());
    }

    @POST
    @Transactional
    public Uni<Response> createUser() {
        return analyticsUserService.create()
                .map(ToUserTransformer::transform)
                .map(u -> Response.status(Response.Status.CREATED).entity(u).build());
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/hello")
    public String hello() {
        return "hello";
    }


}
