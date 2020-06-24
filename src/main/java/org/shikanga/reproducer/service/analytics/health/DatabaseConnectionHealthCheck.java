package org.shikanga.reproducer.service.analytics.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;
import org.shikanga.reproducer.service.analytics.online.user.service.AnalyticsUserService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Duration;

@Readiness
@ApplicationScoped
public class DatabaseConnectionHealthCheck implements HealthCheck {

    @Inject
    AnalyticsUserService analyticsUserService;

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse
                .named("Analytics User Datasource connection health check");

        try {
            Long numberOfAnalyticsUsers = analyticsUserService.getCount()
                    .await()
                    .atMost(Duration.ofSeconds(1));
            responseBuilder.withData("Number of web analytics users in the database", numberOfAnalyticsUsers)
                    .up();
        } catch (IllegalStateException e) {
            responseBuilder.down();
        }

        return responseBuilder.build();
    }
}
