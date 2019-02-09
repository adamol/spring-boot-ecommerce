package com.adamo.ecommerce.web;

import com.adamo.ecommerce.EcommerceApplication;
import com.adamo.ecommerce.email.FakeMailer;
import com.adamo.ecommerce.email.Mailer;
import com.adamo.ecommerce.email.OrderConfirmationEmail;
import com.adamo.ecommerce.logging.FakeLogger;
import com.adamo.ecommerce.logging.LogEntry;
import com.adamo.ecommerce.logging.Logger;
import com.adamo.ecommerce.metrics.FakeMetrics;
import com.adamo.ecommerce.metrics.Metrics;
import com.adamo.ecommerce.metrics.entries.OrderWasPlacedMetric;
import com.adamo.ecommerce.models.InventoryItem;
import com.adamo.ecommerce.models.Item;
import com.adamo.ecommerce.models.Order;
import com.adamo.ecommerce.payment.FakePaymentGateway;
import com.adamo.ecommerce.payment.PaymentGateway;
import com.adamo.ecommerce.repositories.InventoryRepository;
import com.adamo.ecommerce.repositories.ItemsRepository;
import com.adamo.ecommerce.web.requests.PlaceOrderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OrdersCanBePlaced {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ItemsRepository itemsRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    @Qualifier("fake")
    private FakeMailer fakeMailer;

    @Autowired
    @Qualifier("fake")
    private FakeLogger fakeLogger;

    @Autowired
    @Qualifier("fake")
    private FakeMetrics fakeMetrics;

    @Before
    public void clearDatabase() {
        this.itemsRepository.deleteAll();
    }

    @Test
    public void ordersCanBePlaced() throws Exception {

        Item item = itemsRepository.save(new Item("apple", 5));
        inventoryRepository.save(new InventoryItem(item, "test12"));
        inventoryRepository.save(new InventoryItem(item, "test34"));

        PlaceOrderRequest request = new PlaceOrderRequest("john@example.com", item.getId(), 2, "TESTTOKEN1234");

        this.mockMvc
                .perform(
                    post("/orders")
                        .content(this.objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print()).andExpect(status().isOk());

        OrderConfirmationEmail email = (OrderConfirmationEmail) this.fakeMailer.getLatest();
        Assert.assertTrue(email instanceof OrderConfirmationEmail);
        Assert.assertTrue(email.getOrder() instanceof Order);

        LogEntry logEntry = fakeLogger.getLatest();
        Assert.assertNull(logEntry);

        OrderWasPlacedMetric metricEntry = (OrderWasPlacedMetric) fakeMetrics.getLatest();
        Assert.assertTrue(metricEntry instanceof OrderWasPlacedMetric);
        Assert.assertTrue(metricEntry.getOrder() instanceof Order);
    }
}
