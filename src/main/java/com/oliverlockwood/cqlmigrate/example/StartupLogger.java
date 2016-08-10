package com.oliverlockwood.cqlmigrate.example;

import org.joda.time.DateTime;
import org.slf4j.LoggerFactory;

public class StartupLogger {

    static {
        // The first call to DateTime.now() takes ~200ms so don't remove this
        LoggerFactory.getLogger(StartupLogger.class).info("Example application starting {}", DateTime.now());
    }
}