package com.adamo.ecommerce.payment;

import com.adamo.ecommerce.models.Charge;

public interface PaymentGateway {
    Charge process(String paymentToken, Integer totalCost);
}
