package org.shikanga.reproducer.service.analytics.online.event.service;


import org.shikanga.reproducer.service.analytics.online.event.data.AnalyticsEventProperties;
import org.shikanga.reproducer.service.analytics.online.event.domain.EventProperties;

public class ToEventPropertiesTransformer {

    public static EventProperties transform(AnalyticsEventProperties analyticsEventProperties) {
        return EventProperties.builder()
                .id(analyticsEventProperties.getId())
                .label(analyticsEventProperties.getLabel())
                .eventType(analyticsEventProperties.getEventType())
                .category(analyticsEventProperties.getCategory())
                .build();
    }

}
