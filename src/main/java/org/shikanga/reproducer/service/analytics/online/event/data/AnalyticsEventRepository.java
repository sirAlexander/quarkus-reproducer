package org.shikanga.reproducer.service.analytics.online.event.data;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class AnalyticsEventRepository implements PanacheRepository<AnalyticsEvent> {

    List<AnalyticsEvent> findAnalyticsEventByAnalyticsSessionSessionId(String sessionId) {
        return list("select ae from AnalyticsEvent ae where ae.analyticsSession.sessionId = :sessionId",
                Parameters.with("sessionId", sessionId)
        );
    }
}
