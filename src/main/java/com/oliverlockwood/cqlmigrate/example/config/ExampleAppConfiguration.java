package com.oliverlockwood.cqlmigrate.example.config;

import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

public class ExampleAppConfiguration extends Configuration {

    @Valid
    @NotNull
    private Cassandra cassandra;

    public Cassandra getCassandra() {
        return cassandra;
    }

    public static class Cassandra {
        @NotNull
        private String[] hosts;
        @NotEmpty
        private String keyspace;
        @NotNull
        private Integer port;
        @NotNull
        private String username;
        @NotNull
        private String password;
        @NotEmpty
        private List<String> cqlMigrateResources;

        public String[] getHosts() {
            return Arrays.copyOf(hosts, hosts.length);
        }

        public String getKeyspace() {
            return keyspace;
        }

        public Integer getPort() {
            return port;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public List<String> getCqlMigrateResources() {
            return cqlMigrateResources;
        }

        public void setCqlMigrateResources(List<String> cqlMigrateResources) {
            this.cqlMigrateResources = cqlMigrateResources;
        }
    }
}
