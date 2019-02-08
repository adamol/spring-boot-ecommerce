package com.adamo.ecommerce.logging;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Qualifier("fake")
@Service
public class FakeLogger implements Logger {
    private static ArrayList<LogEntry> logs = new ArrayList<>();

    @Override
    public void log(LogEntry log) {
        logs.add(log);
    }

    public LogEntry getLatest() {
        if (logs.size() < 1) {
            return null;
        }

        return logs.get(logs.size() - 1);
    }
}
