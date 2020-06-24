package org.shikanga.reproducer.service.analytics.online.event.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@JsonDeserialize(builder = EventProperties.EventPropertiesBuilder.class)
@Value
@Builder
public class EventProperties {

    @JsonIgnore
    private Long id;

    private String label;
    private String eventType;
    private String category;

    @JsonPOJOBuilder(withPrefix = "")
    public static class EventPropertiesBuilder {}
}
