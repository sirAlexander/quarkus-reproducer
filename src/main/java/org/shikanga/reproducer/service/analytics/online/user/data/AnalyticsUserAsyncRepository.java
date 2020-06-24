package org.shikanga.reproducer.service.analytics.online.user.data;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.concurrent.ExecutorService;

@Singleton
public class AnalyticsUserAsyncRepository {

    private final ExecutorService jdbcExecutor;
    private final AnalyticsUserRepository analyticsUserRepository;

    @Inject
    public AnalyticsUserAsyncRepository(ExecutorService jdbcExecutor,
                                        AnalyticsUserRepository analyticsUserRepository) {
        this.jdbcExecutor = jdbcExecutor;
        this.analyticsUserRepository = analyticsUserRepository;
    }

    public Uni<Long> getCount() {
        Uni<Long> deferred = Uni.createFrom().deferred(
                () -> Uni.createFrom().item(
                        analyticsUserRepository.count()
                ));
        return deferred.emitOn(jdbcExecutor);
    }

    public Uni<AnalyticsUser> findByUserId(String userId) {
        Uni<AnalyticsUser> deferred = Uni.createFrom().deferred(
                () -> Uni.createFrom().optional(
                        analyticsUserRepository.findByUserId(userId)
                ));
        return deferred.emitOn(jdbcExecutor);
    }

    @Transactional
    public Uni<AnalyticsUser> save(AnalyticsUser analyticsUser) {
        Uni<AnalyticsUser> deferred = Uni.createFrom().deferred(
                () -> {
                    analyticsUserRepository.persist(analyticsUser);
                    return Uni.createFrom().item(analyticsUser);
                }
        );
        return deferred.emitOn(jdbcExecutor);
    }

    public Multi<AnalyticsUser> findAllAsync() {
        Multi<AnalyticsUser> deferred = Multi.createFrom().deferred(
                () -> Multi.createFrom().iterable(analyticsUserRepository.listAll())
        );
        return deferred.emitOn(jdbcExecutor);
    }


}
