package com.adamo.ecommerce.metrics.entries;

import com.adamo.ecommerce.metrics.MetricEntry;
import com.adamo.ecommerce.models.Order;

public class OrderWasPlacedMetric implements MetricEntry {

    private Order order;

    public OrderWasPlacedMetric(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}
