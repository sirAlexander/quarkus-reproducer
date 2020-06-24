package org.shikanga.reproducer.service.analytics.online.user.service;


import org.shikanga.reproducer.service.analytics.online.user.data.AnalyticsUser;
import org.shikanga.reproducer.service.analytics.online.user.domain.User;

public class ToUserTransformer {

    public static User transform(AnalyticsUser analyticsUser) {
        return User.builder()
                .id(analyticsUser.getId())
                .userId(analyticsUser.getUserId())
                .build();
    }

}
