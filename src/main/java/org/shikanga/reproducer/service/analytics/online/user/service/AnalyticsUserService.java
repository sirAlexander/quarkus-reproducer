package org.shikanga.reproducer.service.analytics.online.user.service;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.shikanga.reproducer.service.analytics.online.user.data.AnalyticsUser;
import org.shikanga.reproducer.service.analytics.online.user.data.AnalyticsUserAsyncRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;


@Singleton
public final class AnalyticsUserService {

    private final AnalyticsUserAsyncRepository analyticsUserAsyncRepository;


    @Inject
    public AnalyticsUserService(AnalyticsUserAsyncRepository analyticsUserAsyncRepository) {
        this.analyticsUserAsyncRepository = analyticsUserAsyncRepository;
    }

    public Uni<Long> getCount() {
        return analyticsUserAsyncRepository.getCount();
    }

    public Uni<AnalyticsUser> findByUserId(String userId) {
        return analyticsUserAsyncRepository.findByUserId(userId);
    }

    public Uni<AnalyticsUser> create() {
        AnalyticsUser newAnalyticsUser = new AnalyticsUser();
        newAnalyticsUser.setUserId(UUID.randomUUID().toString());
        return analyticsUserAsyncRepository.save(newAnalyticsUser);
    }

    public Uni<AnalyticsUser> update(AnalyticsUser analyticsUser) {
        return analyticsUserAsyncRepository.save(analyticsUser);
    }

    public Multi<AnalyticsUser> findAllAsync() {
        return analyticsUserAsyncRepository.findAllAsync();
    }


}
