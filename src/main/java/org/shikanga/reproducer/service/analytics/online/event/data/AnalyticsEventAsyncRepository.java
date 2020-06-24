package org.shikanga.reproducer.service.analytics.online.event.data;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.concurrent.ExecutorService;

@Singleton
public class AnalyticsEventAsyncRepository {

    private final ExecutorService jdbcExecutor;
    private final AnalyticsEventRepository analyticsEventRepository;

    @Inject
    public AnalyticsEventAsyncRepository(ExecutorService jdbcExecutor,
                                         AnalyticsEventRepository analyticsEventRepository) {
        this.jdbcExecutor = jdbcExecutor;
        this.analyticsEventRepository = analyticsEventRepository;
    }

    public Uni<AnalyticsEvent> save(AnalyticsEvent analyticsEvent) {
        Uni<AnalyticsEvent> deferred = Uni.createFrom().deferred(
                () -> {
                    analyticsEventRepository.persist(analyticsEvent);
                    return Uni.createFrom().item(analyticsEvent);
                }
        );
        return deferred.emitOn(jdbcExecutor);
    }

    public Multi<AnalyticsEvent> findAllByAnalyticsSessionSessionId(String sessionId) {
        Multi<AnalyticsEvent> deferred = Multi.createFrom().deferred(
                () -> {
                    List<AnalyticsEvent> analyticsEvents = analyticsEventRepository.findAnalyticsEventByAnalyticsSessionSessionId(sessionId);
                    return Multi.createFrom().iterable(analyticsEvents);
                }
        );
        return deferred.emitOn(jdbcExecutor);
    }

    public Multi<AnalyticsEvent> findAll() {
        Multi<AnalyticsEvent> deferred = Multi.createFrom().deferred(
                () -> Multi.createFrom().iterable(analyticsEventRepository.listAll())
        );
        return deferred.emitOn(jdbcExecutor);
    }

}
