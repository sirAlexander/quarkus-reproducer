package org.shikanga.reproducer.service.analytics.online.event.service;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;
import org.shikanga.reproducer.service.analytics.online.event.data.AnalyticsEvent;
import org.shikanga.reproducer.service.analytics.online.event.data.AnalyticsEventAsyncRepository;
import org.shikanga.reproducer.service.analytics.online.event.data.AnalyticsEventProperties;
import org.shikanga.reproducer.service.analytics.online.event.domain.Event;
import org.shikanga.reproducer.service.analytics.online.event.domain.EventProperties;
import org.shikanga.reproducer.service.analytics.online.session.data.AnalyticsSession;
import org.shikanga.reproducer.service.analytics.online.session.service.AnalyticsSessionService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public final class AnalyticsEventService {

    private final AnalyticsEventAsyncRepository analyticsEventAsyncRepository;

    private final AnalyticsSessionService analyticsSessionService;

    @Inject
    public AnalyticsEventService(AnalyticsEventAsyncRepository analyticsEventAsyncRepository,
                                 AnalyticsSessionService analyticsSessionService) {
        this.analyticsEventAsyncRepository = analyticsEventAsyncRepository;
        this.analyticsSessionService = analyticsSessionService;
    }

    public Uni<AnalyticsEvent> createEvent(String sessionId, Event event) {
        return analyticsSessionService.findBySessionId(sessionId)
                .map(analyticsSession -> transformToSessionAndEventTuple(event, analyticsSession))
                .flatMap(tuple2 -> analyticsSessionService.save(tuple2.getItem1())
                        .onItem()
                        .produceUni(analyticsSession -> Uni.createFrom().item(tuple2.getItem2()))
                );
    }

    private Tuple2<AnalyticsSession, AnalyticsEvent> transformToSessionAndEventTuple(Event event,
                                                                                     AnalyticsSession analyticsSession) {
        AnalyticsEventProperties analyticsEventProperties = getAnalyticsEventProperties(event);
        AnalyticsEvent analyticsEvent = getAnalyticsEvent(event, analyticsEventProperties);
        analyticsSession.addEvent(analyticsEvent);
        return Tuple2.of(analyticsSession, analyticsEvent);
    }

    private AnalyticsEvent getAnalyticsEvent(Event event,
                                             AnalyticsEventProperties analyticsEventProperties) {
        AnalyticsEvent analyticsEvent = new AnalyticsEvent();
        analyticsEvent.setAction(event.getAction());
        analyticsEvent.setProperties(analyticsEventProperties);
        return analyticsEvent;
    }

    private AnalyticsEventProperties getAnalyticsEventProperties(Event event) {
        EventProperties eventProperties = Optional
                .ofNullable(event.getProperties())
                .orElseGet(() -> EventProperties.builder().build());

        AnalyticsEventProperties analyticsEventProperties = new AnalyticsEventProperties();
        analyticsEventProperties.setLabel(eventProperties.getLabel());
        analyticsEventProperties.setEventType(eventProperties.getEventType());
        analyticsEventProperties.setCategory(eventProperties.getCategory());
        return analyticsEventProperties;
    }

    public Multi<AnalyticsEvent> findAllAsyncBySessionId(String sessionId) {
        return analyticsEventAsyncRepository.findAllByAnalyticsSessionSessionId(sessionId);
    }

}
