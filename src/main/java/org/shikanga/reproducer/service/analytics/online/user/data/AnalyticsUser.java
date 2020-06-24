package org.shikanga.reproducer.service.analytics.online.user.data;


import org.shikanga.reproducer.service.analytics.online.session.data.AnalyticsSession;
import org.shikanga.reproducer.service.persistence.core.GeneratedId;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "analytics_user", uniqueConstraints = {
        @UniqueConstraint(name = "user_id_UNIQUE", columnNames = {"user_id"})
})
public class AnalyticsUser extends GeneratedId implements Serializable {

    public AnalyticsUser() {
        super();
    }

    public AnalyticsUser(String userId) {
        super();
        this.userId = userId;
    }

    private static final long serialVersionUID = 1L;

    @Column(name = "user_id", columnDefinition = "VARCHAR(255)", nullable = false)
    private String userId;

    @OneToMany(mappedBy = "analyticsUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnalyticsSession> sessions = new ArrayList<>();

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<AnalyticsSession> getSessions() {
        return sessions;
    }

    public void setSessions(List<AnalyticsSession> sessions) {
        this.sessions = sessions;
    }

    public void addSession(AnalyticsSession session){
        this.sessions.add(session);
        session.setAnalyticsUser(this);
    }
}
