package org.shikanga.reproducer.service.analytics.online.user;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.shikanga.reproducer.service.analytics.database.TestDatabase;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;


@QuarkusTest
@QuarkusTestResource(TestDatabase.class)
public class UserResourceTest {


    @Test
    public void testPostCreateUserShouldReturn201() {

        given().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .when()
                .post("/users")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body("userId", notNullValue());

    }

}
