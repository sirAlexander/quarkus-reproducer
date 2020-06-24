package org.shikanga.reproducer.service.analytics.online.session.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@JsonDeserialize(builder = ApplicationSettings.ApplicationSettingsBuilder.class)
@Value
@Builder
public class ApplicationSettings {

    private String bookingWidgetId;
    private String bookingWidgetIdentifier;
    private String kioskIdentifier;
    private String customerId;
    private String bookingAttendeeId;
    private String venueId;
    private String productId;

    @JsonProperty("isReschedule")
    private String isReschedule;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ApplicationSettingsBuilder{}

}
