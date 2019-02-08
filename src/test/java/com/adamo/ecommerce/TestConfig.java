package com.adamo.ecommerce;

import com.adamo.ecommerce.email.FakeMailer;
import com.adamo.ecommerce.email.Mailer;
import com.adamo.ecommerce.logging.FakeLogger;
import com.adamo.ecommerce.logging.Logger;
import com.adamo.ecommerce.metrics.FakeMetrics;
import com.adamo.ecommerce.metrics.Metrics;
import com.adamo.ecommerce.payment.FakePaymentGateway;
import com.adamo.ecommerce.payment.PaymentGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

//@Import(EcommerceApplication.class)
@Configuration
@Profile("test")
public class TestConfig {
    @Bean
    @Primary
    public PaymentGateway getPaymentGateway() {
        return new FakePaymentGateway();
    }

    @Bean
    @Primary
    public Mailer getMailer() {
        return new FakeMailer();
    }

    @Bean
    @Primary
    public Logger getLogger() {
        return new FakeLogger();
    }

    @Bean
    @Primary
    public Metrics getMetrics() {
        return new FakeMetrics();
    }
}
