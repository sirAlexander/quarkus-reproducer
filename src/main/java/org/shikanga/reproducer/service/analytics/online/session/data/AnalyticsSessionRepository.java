package org.shikanga.reproducer.service.analytics.online.session.data;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class AnalyticsSessionRepository implements PanacheRepository<AnalyticsSession> {

    Optional<AnalyticsSession> findBySessionId(String sessionId) {
        return find("sessionId", sessionId).singleResultOptional();
    }

}
