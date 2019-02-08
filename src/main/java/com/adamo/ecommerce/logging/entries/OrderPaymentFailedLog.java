package com.adamo.ecommerce.logging.entries;

import com.adamo.ecommerce.logging.LogEntry;
import com.adamo.ecommerce.models.Order;

public class OrderPaymentFailedLog implements LogEntry {

    private Order order;

    public OrderPaymentFailedLog(Order order) {
        this.order = order;
    }
}
