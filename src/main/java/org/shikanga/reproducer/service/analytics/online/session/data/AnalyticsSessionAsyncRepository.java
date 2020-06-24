package org.shikanga.reproducer.service.analytics.online.session.data;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.concurrent.ExecutorService;

@Singleton
public class AnalyticsSessionAsyncRepository {

    private final ExecutorService jdbcExecutor;
    private final AnalyticsSessionRepository analyticsSessionRepository;

    @Inject
    public AnalyticsSessionAsyncRepository(ExecutorService jdbcExecutor,
                                           AnalyticsSessionRepository analyticsSessionRepository) {
        this.jdbcExecutor = jdbcExecutor;
        this.analyticsSessionRepository = analyticsSessionRepository;
    }

    public Uni<AnalyticsSession> findBySessionId(String sessionId) {
        Uni<AnalyticsSession> deferred = Uni.createFrom().deferred(
                () -> Uni.createFrom().optional(
                        analyticsSessionRepository.findBySessionId(sessionId)
                ));
        return deferred.emitOn(jdbcExecutor);
    }

    @Transactional
    public Uni<AnalyticsSession> save(AnalyticsSession analyticsSession) {
        Uni<AnalyticsSession> deferred = Uni.createFrom().deferred(
                () -> {
                    analyticsSessionRepository.persist(analyticsSession);
                    return Uni.createFrom().item(analyticsSession);
                }
        );
        return deferred.emitOn(jdbcExecutor);
    }

    public Multi<AnalyticsSession> findAll() {
        Multi<AnalyticsSession> deferred = Multi.createFrom().deferred(
                () -> Multi.createFrom().iterable(analyticsSessionRepository.listAll())
        );
        return deferred.emitOn(jdbcExecutor);
    }
}
