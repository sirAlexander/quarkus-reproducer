package org.shikanga.reproducer.service.analytics.online.session.service;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.shikanga.reproducer.service.analytics.online.session.data.AnalyticsSession;
import org.shikanga.reproducer.service.analytics.online.session.data.AnalyticsSessionAsyncRepository;
import org.shikanga.reproducer.service.analytics.online.session.domain.ApplicationSettings;
import org.shikanga.reproducer.service.analytics.online.session.domain.Session;
import org.shikanga.reproducer.service.analytics.online.user.data.AnalyticsUser;
import org.shikanga.reproducer.service.analytics.online.user.service.AnalyticsUserService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;
import java.util.UUID;

@Singleton
public final class AnalyticsSessionService {

    private final AnalyticsSessionAsyncRepository analyticsSessionAsyncRepository;

    private final AnalyticsUserService analyticsUserService;

    @Inject
    public AnalyticsSessionService(AnalyticsSessionAsyncRepository analyticsSessionAsyncRepository,
                                   AnalyticsUserService analyticsUserService) {
        this.analyticsSessionAsyncRepository = analyticsSessionAsyncRepository;
        this.analyticsUserService = analyticsUserService;
    }

    public Uni<AnalyticsSession> createNewSession(String userId, Session session) {
        return analyticsUserService.findByUserId(userId)
                .map(analyticsUser -> saveAnalyticsSession(session, analyticsUser));
    }

    private AnalyticsSession saveAnalyticsSession(Session session, AnalyticsUser analyticsUser) {
        AnalyticsSession analyticsSession = new AnalyticsSession();
        analyticsSession.setSessionId(UUID.randomUUID().toString());
        analyticsSession.setBrowser(session.getBrowser());
        analyticsSession.setBrowserVersion(session.getBrowserVersion());
        analyticsSession.setDevice(session.getDevice());
        analyticsSession.setOs(session.getOs());
        analyticsSession.setOsVersion(session.getOsVersion());
        analyticsSession.setCity(session.getCity());
        analyticsSession.setPostalRegion(session.getPostalRegion());
        analyticsSession.setCountry(session.getCountry());
        analyticsSession.setIp(session.getIp());
        analyticsSession.setPath(session.getPath());
        analyticsSession.setReferrer(session.getReferrer());

        analyticsSession.setBookingWidgetSettingsId(Optional
                .ofNullable(session.getApplicationSettings())
                .map(ApplicationSettings::getBookingWidgetId)
                .map(Long::valueOf)
                .orElse(null));

        analyticsSession.setBookingWidgetSettingsIdentifier(Optional
                .ofNullable(session.getApplicationSettings())
                .map(ApplicationSettings::getBookingWidgetIdentifier)
                .orElse(null));

        analyticsSession.setKioskIdentifier(Optional
                .ofNullable(session.getApplicationSettings())
                .map(ApplicationSettings::getKioskIdentifier)
                .orElse(null));

        analyticsSession.setBookingAttendeeId(Optional
                .ofNullable(session.getApplicationSettings())
                .map(ApplicationSettings::getBookingAttendeeId)
                .map(Long::valueOf)
                .orElse(null));

        analyticsSession.setCustomerId(Optional
                .ofNullable(session.getApplicationSettings())
                .map(ApplicationSettings::getCustomerId)
                .map(Long::valueOf)
                .orElse(null));

        analyticsSession.setVenueId(Optional
                .ofNullable(session.getApplicationSettings())
                .map(ApplicationSettings::getVenueId)
                .map(Long::valueOf)
                .orElse(null));

        analyticsSession.setProductId(Optional
                .ofNullable(session.getApplicationSettings())
                .map(ApplicationSettings::getProductId)
                .map(Long::valueOf)
                .orElse(null));

        analyticsSession.setReschedule(Optional
                .ofNullable(session.getApplicationSettings())
                .map(ApplicationSettings::getIsReschedule)
                .map(Boolean::valueOf)
                .orElse(false));

        analyticsUser.addSession(analyticsSession);
        analyticsUserService.update(analyticsUser);

        return analyticsSession;
    }

    public Uni<AnalyticsSession> findBySessionId(String sessionId) {
        return analyticsSessionAsyncRepository.findBySessionId(sessionId);
    }

    public Uni<AnalyticsSession> save(AnalyticsSession analyticsSession) {
        return analyticsSessionAsyncRepository.save(analyticsSession);
    }

    public Multi<AnalyticsSession> findAllAsync() {
        return analyticsSessionAsyncRepository.findAll();
    }
}
