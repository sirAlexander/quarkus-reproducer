package org.shikanga.reproducer.service.analytics.online.user.data;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class AnalyticsUserRepository implements PanacheRepository<AnalyticsUser> {

    Optional<AnalyticsUser> findByUserId(String userId) {
        return find("userId", userId).singleResultOptional();
    }
}
