package org.shikanga.reproducer.service.analytics.online.event.data;


import org.shikanga.reproducer.service.analytics.online.session.data.AnalyticsSession;
import org.shikanga.reproducer.service.persistence.core.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "analytics_event")
public class AnalyticsEvent extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    public Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private AnalyticsEventProperties properties;

    @Column(name = "action", columnDefinition = "VARCHAR(255) DEFAULT NULL")
    private String action;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analytics_session_id", columnDefinition = "BIGINT(20) NOT NULL")
    private AnalyticsSession analyticsSession;


    public Long getId() {
        return id;
    }

    public AnalyticsEventProperties getProperties() {
        return properties;
    }

    public void setProperties(AnalyticsEventProperties properties) {
        this.properties = properties;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public AnalyticsSession getAnalyticsSession() {
        return analyticsSession;
    }

    public void setAnalyticsSession(AnalyticsSession analyticsSession) {
        this.analyticsSession = analyticsSession;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnalyticsEvent that = (AnalyticsEvent) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
