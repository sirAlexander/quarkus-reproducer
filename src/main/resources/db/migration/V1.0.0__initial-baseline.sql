--
-- Table structure for table `analytics_user`
--
CREATE TABLE IF NOT EXISTS `analytics_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `archived` tinyint(1) DEFAULT '0',
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_updated_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `version` int(11) DEFAULT NULL,
  `user_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


--
-- Table structure for table `event_properties`
--
CREATE TABLE IF NOT EXISTS `event_properties` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `archived` tinyint(1) DEFAULT '0',
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_updated_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `version` int(11) DEFAULT NULL,
  `category` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `event_type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `label` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `token` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


--
-- Table structure for table `analytics_session`
--
CREATE TABLE IF NOT EXISTS `analytics_session` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `archived` tinyint(1) DEFAULT '0',
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_updated_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `version` int(11) DEFAULT NULL,
  `booking_attendee_id` bigint(20) DEFAULT NULL,
  `booking_series_id` bigint(20) DEFAULT NULL,
  `booking_series_identifier` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `booking_widget_settings_id` bigint(20) DEFAULT NULL,
  `booking_widget_settings_identifier` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `browser` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `browser_version` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `city` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `country` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  `device` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ip` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `kiosk_identifier` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `merchant_id` bigint(20) DEFAULT NULL,
  `os` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `os_version` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `path` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `postal_region` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  `referrer` longtext COLLATE utf8mb4_unicode_ci,
  `reschedule` tinyint(1) DEFAULT '0',
  `session_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `venue_id` bigint(20) DEFAULT NULL,
  `analytics_user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_analytics_session_user_id` (`analytics_user_id`),
  CONSTRAINT `FK_analytics_session_user_id` FOREIGN KEY (`analytics_user_id`) REFERENCES `analytics_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Table structure for table `analytics_event`
--
CREATE TABLE IF NOT EXISTS `analytics_event` (
  `archived` tinyint(1) DEFAULT '0',
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_updated_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `version` int(11) DEFAULT NULL,
  `action` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `properties_id` bigint(20) NOT NULL,
  `analytics_session_id` bigint(20) NOT NULL,
  PRIMARY KEY (`properties_id`),
  KEY `FK_analytics_event_session_id` (`analytics_session_id`),
  CONSTRAINT `FK_analytics_event_session_id` FOREIGN KEY (`analytics_session_id`) REFERENCES `analytics_session` (`id`),
  CONSTRAINT `FK_analytics_event_properties_id` FOREIGN KEY (`properties_id`) REFERENCES `event_properties` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


