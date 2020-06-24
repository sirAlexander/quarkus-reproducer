package org.shikanga.reproducer.service.analytics.online.session.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import org.shikanga.reproducer.service.analytics.online.event.domain.Event;

import java.util.ArrayList;
import java.util.List;


@JsonDeserialize(builder = Session.SessionBuilder.class)
@Value
@Builder(toBuilder = true)
public class Session {

    @JsonIgnore
    private Long id;

    private String sessionId;
    private String applicationId;

    @Builder.Default
    private ApplicationSettings applicationSettings = ApplicationSettings.builder().build();

    private String browser;
    private String browserVersion;
    private String device;
    private String os;
    private String osVersion;

    private String city;
    private String postalRegion;
    private String country;
    private String ip;
    private String path;
    private String referrer;

    @Builder.Default
    private List<Event> events = new ArrayList<>();

    @JsonPOJOBuilder(withPrefix = "")
    public static class SessionBuilder {}

}
