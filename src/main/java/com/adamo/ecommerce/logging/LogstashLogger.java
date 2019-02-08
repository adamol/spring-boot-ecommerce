package com.adamo.ecommerce.logging;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("default")
public class LogstashLogger implements Logger {
    @Override
    public void log(LogEntry log) {

    }
}
