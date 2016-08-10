package com.oliverlockwood.cqlmigrate.example;

import com.oliverlockwood.cqlmigrate.example.bundles.MigrateSchemaBundle;
import com.oliverlockwood.cqlmigrate.example.config.ExampleAppConfiguration;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleApplication extends Application<ExampleAppConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleApplication.class);

    public static void main(String[] args) throws Exception {
        new ExampleApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<ExampleAppConfiguration> bootstrap) {
        bootstrap.addBundle(new MigrateSchemaBundle());
    }

    @Override
    public void run(ExampleAppConfiguration configuration, Environment environment) throws Exception {}
}
