package com.oliverlockwood.cqlmigrate.example.bundles;

import com.google.common.collect.ImmutableMap;
import com.oliverlockwood.cqlmigrate.example.config.ExampleAppConfiguration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.configuration.ConfigurationException;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.sky.cqlmigrate.CassandraLockConfig;
import uk.sky.cqlmigrate.CqlMigrator;
import uk.sky.cqlmigrate.CqlMigratorFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.spi.FileSystemProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class MigrateSchemaBundle implements ConfiguredBundle<ExampleAppConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MigrateSchemaBundle.class);

    @Override
    public void run(ExampleAppConfiguration configuration, Environment environment) throws Exception {

        for (FileSystemProvider provider : FileSystemProvider.installedProviders()) {
            LOGGER.info("Provider {}", provider.getScheme());
        }

        migrate(configuration.getCassandra());
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {
    }

    private void migrate(ExampleAppConfiguration.Cassandra cassandra) throws IOException, ConfigurationException, URISyntaxException {
        LOGGER.info("Migrating {} on {} from {}", cassandra.getKeyspace(), Arrays.asList(cassandra.getHosts()), cassandra.getCqlMigrateResources());
        ClassLoader classLoader = this.getClass().getClassLoader();
        String classLocationAsString = classLocationAsString(MigrateSchemaBundle.class);
        boolean isInJarFile = classLocationAsString.endsWith(".jar");
        if (isInJarFile) {
            URI fileSystemUri = new URI("jar", Paths.get(classLocationAsString).toUri().toString(), null);
            try (FileSystem fileSystem = FileSystems.newFileSystem(fileSystemUri, ImmutableMap.of("create", "true"))) {
                findResourcesAndRunMigration(cassandra, cqlMigrateResource -> fileSystem.getPath(cqlMigrateResource));
            }
        } else {
            findResourcesAndRunMigration(cassandra, cqlMigrateResource -> getPathFromClassloader(classLoader, cqlMigrateResource));
        }
    }

    private void findResourcesAndRunMigration(ExampleAppConfiguration.Cassandra cassandra, Function<String, Path> pathFindingFunction) {
        CqlMigrator cqlMigrator = CqlMigratorFactory.create(CassandraLockConfig.builder().build());
        List<Path> resourcePaths = new ArrayList<>();
        for (String cqlMigrateResource : cassandra.getCqlMigrateResources()) {
            Path resourcePath = pathFindingFunction.apply(cqlMigrateResource);
            resourcePaths.add(resourcePath);
        }
        cqlMigrator.migrate(cassandra.getHosts(), cassandra.getPort(), cassandra.getUsername(), cassandra.getPassword(), cassandra.getKeyspace(), resourcePaths);
    }

    private Path getPathFromClassloader(ClassLoader classLoader, String cqlMigrateResource) {
        URL resource = classLoader.getResource(cqlMigrateResource);
        try {
            return Paths.get(resource.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Unable to get path for resource " + resource);
        }
    }

    private String classLocationAsString(Class<?> classToCheck) {
        String classFullNameAsResource = '/' + classToCheck.getName().replace('.', '/') + ".class";
        URL classLocationFullUrl = classToCheck.getResource(classFullNameAsResource);
        return classLocationFullUrl.getFile().replaceFirst("file:", "").replaceAll("!" + classFullNameAsResource, "");
    }
}
