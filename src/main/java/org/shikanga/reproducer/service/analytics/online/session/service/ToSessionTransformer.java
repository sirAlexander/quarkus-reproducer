package org.shikanga.reproducer.service.analytics.online.session.service;

import org.shikanga.reproducer.service.analytics.online.session.data.AnalyticsSession;
import org.shikanga.reproducer.service.analytics.online.session.domain.ApplicationSettings;
import org.shikanga.reproducer.service.analytics.online.session.domain.Session;

import java.util.Optional;

public class ToSessionTransformer {

    public static Session transform(AnalyticsSession analyticsSession) {
        return Session.builder()
                .id(analyticsSession.getId())
                .sessionId(analyticsSession.getSessionId())

                .browser(analyticsSession.getBrowser())
                .browserVersion(analyticsSession.getBrowserVersion())
                .device(analyticsSession.getDevice())
                .os(analyticsSession.getOs())
                .osVersion(analyticsSession.getOsVersion())

                .city(analyticsSession.getCity())
                .postalRegion(analyticsSession.getPostalRegion())
                .country(analyticsSession.getCountry())
                .ip(analyticsSession.getIp())
                .path(analyticsSession.getPath())
                .referrer(analyticsSession.getReferrer())

                .applicationSettings(ApplicationSettings.builder()
                        .bookingWidgetId(Optional
                                .ofNullable(analyticsSession.getBookingWidgetSettingsId())
                                .map(String::valueOf)
                                .orElse(null))
                        .bookingWidgetIdentifier(analyticsSession.getBookingWidgetSettingsIdentifier())
                        .kioskIdentifier(analyticsSession.getKioskIdentifier())
                        .bookingAttendeeId(Optional
                                .ofNullable(analyticsSession.getBookingAttendeeId())
                                .map(String::valueOf)
                                .orElse(null))
                        .customerId(Optional
                                .ofNullable(analyticsSession.getCustomerId())
                                .map(String::valueOf)
                                .orElse(null))
                        .venueId(Optional
                                .ofNullable(analyticsSession.getVenueId())
                                .map(String::valueOf)
                                .orElse(null))
                        .productId(Optional
                                .ofNullable(analyticsSession.getProductId())
                                .map(String::valueOf)
                                .orElse(null))
                        .isReschedule(Optional
                                .of(analyticsSession.isReschedule())
                                .map(String::valueOf)
                                .orElse(null))
                        .build())

                .build();
    }
}
