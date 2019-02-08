package com.adamo.ecommerce.email;

import com.adamo.ecommerce.models.Order;

import java.util.Map;

public class OrderConfirmationEmail implements Email {

    private Order order;

    public OrderConfirmationEmail(Order order) {
        this.order = order;
    }

    @Override
    public Map<String, Object> getData() {
        return null;
    }

    public Order getOrder() {
        return order;
    }
}
