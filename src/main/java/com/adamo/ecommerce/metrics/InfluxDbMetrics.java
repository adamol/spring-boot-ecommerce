package com.adamo.ecommerce.metrics;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("default")
public class InfluxDbMetrics implements Metrics {

    @Override
    public void add(MetricEntry entry) {

    }
}
