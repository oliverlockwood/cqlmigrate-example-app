package com.oliverlockwood.cqlmigrate.example;

import com.oliverlockwood.cqlmigrate.example.bundles.MigrateSchemaBundle;
import com.oliverlockwood.cqlmigrate.example.config.ExampleAppConfiguration;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ExampleApplication extends Application<ExampleAppConfiguration> {

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
