package org.shikanga.reproducer.service.analytics.online.session.data;


import org.shikanga.reproducer.service.analytics.online.event.data.AnalyticsEvent;
import org.shikanga.reproducer.service.analytics.online.user.data.AnalyticsUser;
import org.shikanga.reproducer.service.persistence.core.GeneratedId;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "analytics_session", uniqueConstraints = {
        @UniqueConstraint(name = "session_id_UNIQUE", columnNames = {"session_id"})
})
public class AnalyticsSession extends GeneratedId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "browser", columnDefinition = "VARCHAR(255) DEFAULT NULL")
    private String browser;

    @Column(name = "browser_version", columnDefinition = "VARCHAR(255) DEFAULT NULL")
    private String browserVersion;

    @Column(name = "city", columnDefinition = "VARCHAR(255) DEFAULT NULL")
    private String city;

    @Column(name = "country", columnDefinition = "VARCHAR(255) DEFAULT NULL")
    private String country;

    @Column(name = "device", columnDefinition = "VARCHAR(255) DEFAULT NULL")
    private String device;

    @Column(name = "ip", columnDefinition = "VARCHAR(255) DEFAULT NULL")
    private String ip;

    @Column(name = "kiosk_identifier", columnDefinition = "VARCHAR(255) DEFAULT NULL")
    private String kioskIdentifier;

    @Column(name = "os", columnDefinition = "VARCHAR(255) DEFAULT NULL")
    private String os;

    @Column(name = "os_version", columnDefinition = "VARCHAR(255) DEFAULT NULL")
    private String osVersion;

    @Column(name = "path", columnDefinition = "VARCHAR(255) DEFAULT NULL")
    private String path;

    @Column(name = "postal_region", columnDefinition = "VARCHAR(255) DEFAULT NULL")
    private String postalRegion;

    @Column(name = "referrer", columnDefinition = "LONGTEXT")
    private String referrer;

    @Column(name = "session_id", columnDefinition = "VARCHAR(255) DEFAULT NULL")
    private String sessionId;

    @Column(name = "booking_series_identifier", columnDefinition = "VARCHAR(255) DEFAULT NULL")
    private String bookingSeriesIdentifier;

    @Column(name = "booking_widget_settings_identifier", columnDefinition = "VARCHAR(255) DEFAULT NULL")
    private String bookingWidgetSettingsIdentifier;

    @Column(name = "booking_series_id", columnDefinition = "BIGINT(20) DEFAULT NULL")
    private Long bookingSeriesId;

    @Column(name = "booking_widget_settings_id", columnDefinition = "BIGINT(20) DEFAULT NULL")
    private Long bookingWidgetSettingsId;

    @Column(name = "merchant_id", columnDefinition = "BIGINT(20) DEFAULT NULL")
    private Long merchantId;

    @Deprecated // TODO: This doesn't actually exist in QudiniApp version, probably can be removed
    @Column(name = "product_id", columnDefinition = "BIGINT(20) DEFAULT NULL")
    private Long productId;

    @Deprecated // TODO: This doesn't actually exist in QudiniApp version, probably can be removed
    @Column(name = "booking_attendee_id", columnDefinition = "BIGINT(20) DEFAULT NULL")
    private Long bookingAttendeeId;

    @Column(name = "customer_id", columnDefinition = "BIGINT(20) DEFAULT NULL")
    private Long customerId;

    @Deprecated // TODO: This doesn't actually exist in QudiniApp version, probably can be removed
    @Column(name = "venue_id", columnDefinition = "BIGINT(20) DEFAULT NULL")
    private Long venueId;

    @Deprecated // TODO: This doesn't actually exist in QudiniApp version, probably can be removed
    @Column(name = "reschedule", columnDefinition = "TINYINT(1) DEFAULT '0'")
    private boolean reschedule = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analytics_user_id", columnDefinition = "BIGINT(20) NOT NULL")
    private AnalyticsUser analyticsUser;

    @OneToMany(mappedBy = "analyticsSession", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnalyticsEvent> events = new ArrayList<>();

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getKioskIdentifier() {
        return kioskIdentifier;
    }

    public void setKioskIdentifier(String kioskIdentifier) {
        this.kioskIdentifier = kioskIdentifier;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPostalRegion() {
        return postalRegion;
    }

    public void setPostalRegion(String postalRegion) {
        this.postalRegion = postalRegion;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getBookingSeriesIdentifier() {
        return bookingSeriesIdentifier;
    }

    public void setBookingSeriesIdentifier(String bookingSeriesIdentifier) {
        this.bookingSeriesIdentifier = bookingSeriesIdentifier;
    }

    public String getBookingWidgetSettingsIdentifier() {
        return bookingWidgetSettingsIdentifier;
    }

    public void setBookingWidgetSettingsIdentifier(String bookingWidgetSettingsIdentifier) {
        this.bookingWidgetSettingsIdentifier = bookingWidgetSettingsIdentifier;
    }

    public Long getBookingSeriesId() {
        return bookingSeriesId;
    }

    public void setBookingSeriesId(Long bookingSeriesId) {
        this.bookingSeriesId = bookingSeriesId;
    }

    public Long getBookingWidgetSettingsId() {
        return bookingWidgetSettingsId;
    }

    public void setBookingWidgetSettingsId(Long bookingWidgetSettingsId) {
        this.bookingWidgetSettingsId = bookingWidgetSettingsId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getBookingAttendeeId() {
        return bookingAttendeeId;
    }

    public void setBookingAttendeeId(Long bookingAttendeeId) {
        this.bookingAttendeeId = bookingAttendeeId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getVenueId() {
        return venueId;
    }

    public void setVenueId(Long venueId) {
        this.venueId = venueId;
    }

    public boolean isReschedule() {
        return reschedule;
    }

    public void setReschedule(boolean reschedule) {
        this.reschedule = reschedule;
    }

    public AnalyticsUser getAnalyticsUser() {
        return analyticsUser;
    }

    public void setAnalyticsUser(AnalyticsUser analyticsUser) {
        this.analyticsUser = analyticsUser;
    }

    public List<AnalyticsEvent> getEvents() {
        return events;
    }

    public void setEvents(List<AnalyticsEvent> events) {
        this.events = events;
    }

    public void addEvent(AnalyticsEvent analyticsEvent) {
        events.add(analyticsEvent);
        analyticsEvent.setAnalyticsSession(this);
    }

    public void removeEvent(AnalyticsEvent analyticsEvent) {
        events.remove(analyticsEvent);
        analyticsEvent.setAnalyticsSession(null);
    }
}
