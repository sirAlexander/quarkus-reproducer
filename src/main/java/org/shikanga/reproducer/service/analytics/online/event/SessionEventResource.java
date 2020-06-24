package org.shikanga.reproducer.service.analytics.online.event;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.shikanga.reproducer.service.analytics.online.event.domain.Event;
import org.shikanga.reproducer.service.analytics.online.event.service.AnalyticsEventService;
import org.shikanga.reproducer.service.analytics.online.event.service.ToEventTransformer;

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

@Path("/sessions/{sessionId}/events")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SessionEventResource {

    @Inject
    AnalyticsEventService analyticsEventService;

    @POST
    @Transactional
    public Uni<Response> createEvent(@PathParam("sessionId") String sessionId,
                                     Event event) {
        return analyticsEventService
                .createEvent(sessionId, event)
                .map(analyticsEvent -> ToEventTransformer.transform(analyticsEvent, analyticsEvent.getProperties()))
                .map(e -> Response.status(Response.Status.CREATED).entity(e).build());

    }

    @GET
    public Multi<Event> getAllEvents(@PathParam("sessionId") String sessionId) {
        return analyticsEventService.findAllAsyncBySessionId(sessionId)
                .map(analyticsEvent -> ToEventTransformer.transform(analyticsEvent, analyticsEvent.getProperties()));
    }

}
