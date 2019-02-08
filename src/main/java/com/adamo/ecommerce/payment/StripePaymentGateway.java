package com.adamo.ecommerce.payment;

import com.adamo.ecommerce.models.Charge;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("default")
public class StripePaymentGateway implements PaymentGateway {

    @Override
    public Charge process(String paymentToken, Integer totalCost) {
        return new Charge(1234);
    }
}
