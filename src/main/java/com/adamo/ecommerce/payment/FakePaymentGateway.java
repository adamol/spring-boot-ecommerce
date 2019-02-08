package com.adamo.ecommerce.payment;

import com.adamo.ecommerce.models.Charge;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("fake")
public class FakePaymentGateway implements PaymentGateway {

    @Override
    public Charge process(String paymentToken, Integer totalCost) {
        System.out.println("===================================");
        System.out.println("FakePaymentGateway called" +
                "");
        System.out.println("===================================");
        return new Charge(1234);
    }
}
