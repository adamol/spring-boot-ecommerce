package com.adamo.ecommerce.metrics;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Qualifier("fake")
public class FakeMetrics implements Metrics {

    private static ArrayList<MetricEntry> metrics = new ArrayList<>();

    @Override
    public void add(MetricEntry entry) {
        metrics.add(entry);
    }

    public MetricEntry getLatest() {
        if (metrics.size() < 1) {
            return null;
        }

        return metrics.get(metrics.size() - 1);
    }
}
