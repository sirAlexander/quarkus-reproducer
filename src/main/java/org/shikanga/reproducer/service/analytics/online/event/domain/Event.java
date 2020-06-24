package org.shikanga.reproducer.service.analytics.online.event.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@JsonDeserialize(builder = Event.EventBuilder.class)
@Value
@Builder
public class Event {

    @JsonIgnore
    private Long id;

    private String action;

    @Builder.Default
    private EventProperties properties = EventProperties.builder().build();

    @JsonPOJOBuilder(withPrefix = "")
    public static class EventBuilder{}
}
