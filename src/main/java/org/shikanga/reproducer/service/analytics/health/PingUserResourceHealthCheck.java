package org.shikanga.reproducer.service.analytics.health;


import org.shikanga.reproducer.service.analytics.online.user.UserResource;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Liveness
@ApplicationScoped
public class PingUserResourceHealthCheck implements HealthCheck {

    @Inject
    UserResource userResource;

    @Override
    public HealthCheckResponse call() {
        userResource.hello();
        return HealthCheckResponse.named("Ping Web Analytics User REST Endpoint ")
                .up()
                .build();
    }
}
