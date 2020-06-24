package org.shikanga.reproducer.service.analytics.online.event;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.shikanga.reproducer.service.analytics.database.TestDatabase;
import org.shikanga.reproducer.service.analytics.online.event.domain.Event;
import org.shikanga.reproducer.service.analytics.online.event.domain.EventProperties;
import org.shikanga.reproducer.service.analytics.online.session.data.AnalyticsSession;
import org.shikanga.reproducer.service.analytics.online.session.service.AnalyticsSessionService;
import org.shikanga.reproducer.service.analytics.online.user.data.AnalyticsUser;
import org.shikanga.reproducer.service.analytics.online.user.service.AnalyticsUserService;

import javax.inject.Inject;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Duration;
import java.util.UUID;

import static io.restassured.RestAssured.given;

@QuarkusTest
@QuarkusTestResource(TestDatabase.class)
public class SessionEventResourceTest {

    @Inject
    AnalyticsUserService analyticsUserService;

    @Inject
    AnalyticsSessionService analyticsSessionService;

    @Test
    public void testPostCreateEventShouldReturn201() {

        String sessionId = UUID.randomUUID().toString();
        Event request = getAnalyticsEventRequest();

        AnalyticsSession analyticsSession = createAnalyticsSession(sessionId);

        Event response = given().body(request)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .when()
                .pathParam("sessionId", analyticsSession.getSessionId())
                .post("/sessions/{sessionId}/events")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .extract()
                .as(Event.class);

        Assert.assertEquals(request, response);
    }

    private AnalyticsSession createAnalyticsSession(String sessionId) {

        AnalyticsSession analyticsSession = new AnalyticsSession();
        analyticsSession.setSessionId(sessionId);
        analyticsSession.setVenueId(1L);
        analyticsSession.setProductId(2L);
        analyticsSession.setReschedule(true);
        analyticsSession.setBookingWidgetSettingsIdentifier("IDENTIFIER");
        analyticsSession.setBrowser("Firefox");
        analyticsSession.setBrowserVersion("70.0");
        analyticsSession.setDevice("Mac");
        analyticsSession.setOs("macOS");
        analyticsSession.setOsVersion("10.14");
        analyticsSession.setReferrer("https://from-site.info");

        AnalyticsUser analyticsUser = createAnalyticsUser(UUID.randomUUID().toString());
        analyticsUser.addSession(analyticsSession);

        return analyticsSessionService.save(analyticsSession)
                .await()
                .atMost(Duration.ofSeconds(1));
    }

    private AnalyticsUser createAnalyticsUser(String userId) {
        return analyticsUserService.update(new AnalyticsUser(userId))
                .await()
                .atMost(Duration.ofSeconds(1));
    }

    private Event getAnalyticsEventRequest() {
        return Event.builder()
                .action("Delete Booking")
                .properties(EventProperties.builder()
                        .label("Click here to cancel a booking")
                        .eventType("Click")
                        .category("Bookings")
                        .build()
                ).build();
    }
}