package org.shikanga.reproducer.service.analytics.online.event.data;


import org.shikanga.reproducer.service.persistence.core.GeneratedId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "event_properties")
public class AnalyticsEventProperties extends GeneratedId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "label", columnDefinition = "VARCHAR(255) DEFAULT NULL")
    private String label;

    @Column(name = "event_type", columnDefinition = "VARCHAR(255) DEFAULT NULL")
    private String eventType;

    @Column(name = "category", columnDefinition = "VARCHAR(255) DEFAULT NULL")
    private String category;

    @Column(name = "token", columnDefinition = "VARCHAR(255) DEFAULT NULL")
    private String token;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
