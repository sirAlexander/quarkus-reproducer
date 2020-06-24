package org.shikanga.reproducer.service.analytics.database;


import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ApplicationScoped
public class DatabaseConfig {

    @ConfigProperty(name = "quarkus.datasource.jdbc.max-size")
    protected int connectionPoolSize;

    @Produces
    public ExecutorService jdbcExecutor() {
        return Executors.newFixedThreadPool(connectionPoolSize);
    }

}