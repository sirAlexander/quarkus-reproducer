package org.shikanga.reproducer.service.persistence.core;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Version
    private Integer version;

    @Column(name = "archived", columnDefinition = "TINYINT(1) DEFAULT '0'")
    private boolean archived = false;

    @NotNull
    @Column(name = "created_date_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdDateTime = LocalDateTime.now();

    @NotNull
    @Column(name = "last_updated_date_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime lastUpdatedDateTime = LocalDateTime.now();


    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public LocalDateTime getLastUpdatedDateTime() {
        return lastUpdatedDateTime;
    }

    public void setLastUpdatedDateTime(LocalDateTime lastUpdatedDateTime) {
        this.lastUpdatedDateTime = lastUpdatedDateTime;
    }

    @PrePersist
    private void prePersist() {
        if (createdDateTime == null) {
            createdDateTime = LocalDateTime.now();
        }
        lastUpdatedDateTime = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdate() {
        lastUpdatedDateTime = LocalDateTime.now();
    }
}