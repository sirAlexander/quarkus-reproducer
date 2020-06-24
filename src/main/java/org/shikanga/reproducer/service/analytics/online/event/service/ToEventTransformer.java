package org.shikanga.reproducer.service.analytics.online.event.service;


import org.shikanga.reproducer.service.analytics.online.event.data.AnalyticsEvent;
import org.shikanga.reproducer.service.analytics.online.event.data.AnalyticsEventProperties;
import org.shikanga.reproducer.service.analytics.online.event.domain.Event;

public class ToEventTransformer {

    public static Event transform(AnalyticsEvent analyticsEvent) {
        return Event.builder()
                .id(analyticsEvent.getId())
                .action(analyticsEvent.getAction())
                .build();
    }

    public static Event transform(AnalyticsEvent analyticsEvent,
                                  AnalyticsEventProperties analyticsEventProperties) {
        return Event.builder()
                .id(analyticsEvent.getId())
                .action(analyticsEvent.getAction())
                .properties(ToEventPropertiesTransformer.transform(analyticsEventProperties))
                .build();
    }

}
