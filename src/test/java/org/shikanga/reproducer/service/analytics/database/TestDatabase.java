package org.shikanga.reproducer.service.analytics.database;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.MySQLContainer;

import java.util.HashMap;
import java.util.Map;

public class TestDatabase implements QuarkusTestResourceLifecycleManager {

    public static final MySQLContainer<?> DATABASE = new MySQLContainer<>("mysql:5.7.22")
            .withDatabaseName("test_qudini_mp_web_analytics")
            .withUsername("qudini_web_analytics")
            .withPassword("password");


    @Override
    public Map<String, String> start() {
        DATABASE.start();
        Map<String, String> datasourceProperties = new HashMap<>();
        datasourceProperties.put("quarkus.datasource.username", DATABASE.getUsername());
        datasourceProperties.put("quarkus.datasource.password", DATABASE.getPassword());
        datasourceProperties.put("quarkus.datasource.jdbc.url", DATABASE.getJdbcUrl());
        return datasourceProperties;
    }

    @Override
    public void stop() {
        DATABASE.stop();
    }
}
