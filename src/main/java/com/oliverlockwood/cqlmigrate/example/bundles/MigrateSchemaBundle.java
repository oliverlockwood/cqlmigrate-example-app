package com.oliverlockwood.cqlmigrate.example.bundles;

import com.google.common.collect.ImmutableMap;
import com.oliverlockwood.cqlmigrate.example.config.ExampleAppConfiguration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.sky.cqlmigrate.CassandraLockConfig;
import uk.sky.cqlmigrate.CqlMigrator;
import uk.sky.cqlmigrate.CqlMigratorFactory;

import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MigrateSchemaBundle implements ConfiguredBundle<ExampleAppConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MigrateSchemaBundle.class);

    @Override
    public void initialize(Bootstrap<?> bootstrap) {}

    @Override
    public void run(ExampleAppConfiguration configuration, Environment environment) throws Exception {
        ExampleAppConfiguration.Cassandra cassandra = configuration.getCassandra();
        LOGGER.info("Migrating {} on {} from {}",
                    cassandra.getKeyspace(), Arrays.asList(cassandra.getHosts()), cassandra.getCqlMigrateResources());

        // Turn configured resource folders into Path objects
        URL resource = this.getClass().getResource("");
        FileSystem fs = FileSystems.newFileSystem(resource.toURI(), ImmutableMap.of("create", "true"));
        List<Path> cqlPaths = cassandra.getCqlMigrateResources().stream()
            .map(cqlMigrateResource -> fs.getPath(cqlMigrateResource))
            .collect(Collectors.toList());

        // Run cqlmigrate
        CqlMigrator cqlMigrator = CqlMigratorFactory.create(CassandraLockConfig.builder().build());
        cqlMigrator.migrate(cassandra.getHosts(), cassandra.getPort(),
                            cassandra.getUsername(), cassandra.getPassword(),
                            cassandra.getKeyspace(), cqlPaths);
    }
}
