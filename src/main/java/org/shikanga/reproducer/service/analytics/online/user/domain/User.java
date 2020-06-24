package org.shikanga.reproducer.service.analytics.online.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

@JsonDeserialize(builder = User.UserBuilder.class)
@Value
@Builder(toBuilder = true)
public class User {

    @JsonIgnore
    private Long id;

    private String userId;
}
