package org.shikanga.reproducer.service.analytics.online.session;


import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.shikanga.reproducer.service.analytics.database.TestDatabase;
import org.shikanga.reproducer.service.analytics.online.session.domain.ApplicationSettings;
import org.shikanga.reproducer.service.analytics.online.session.domain.Session;
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
public class UserSessionResourceTest {

    @Inject
    AnalyticsUserService analyticsUserService;

    @Test
    public void testPostCreateSessionForExistingUserShouldReturn201() {
        String userId = UUID.randomUUID().toString();
        String bwIdentifier = "IDENTIFIER";

        Session request = getAnalyticsSessionRequest(bwIdentifier);

        AnalyticsUser analyticsUser = createAnalyticsUser(userId);

        Session response = given().body(request)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .when()
                .pathParam("userId", analyticsUser.getUserId())
                .post("/users/{userId}/sessions")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .extract()
                .as(Session.class);

        Session expectedResponse = request.toBuilder()
                .applicationId(null)
                .sessionId(response.getSessionId())
                .build();

        Assert.assertEquals(expectedResponse, response);
    }

    private Session getAnalyticsSessionRequest(String bwIdentifier) {

        return Session.builder()
                .applicationId("BookingWidget")
                .applicationSettings(ApplicationSettings.builder()
                        .venueId("1")
                        .productId("2")
                        .isReschedule(Boolean.toString(true))
                        .bookingWidgetIdentifier(bwIdentifier)
                        .build())
                .browser("Firefox")
                .browserVersion("70.0")
                .device("Mac")
                .os("macOS")
                .osVersion("10.14")
                .referrer("https://from-site.info")
                .build();
    }

    private AnalyticsUser createAnalyticsUser(String userId) {
        return analyticsUserService.update(new AnalyticsUser(userId))
                .await()
                .atMost(Duration.ofSeconds(1));
    }


}