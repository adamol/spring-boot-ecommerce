package com.adamo.ecommerce.web;

import com.adamo.ecommerce.email.OrderConfirmationEmail;
import com.adamo.ecommerce.logging.Logger;
import com.adamo.ecommerce.email.Mailer;
import com.adamo.ecommerce.metrics.Metrics;
import com.adamo.ecommerce.models.Charge;
import com.adamo.ecommerce.models.InventoryItem;
import com.adamo.ecommerce.models.Item;
import com.adamo.ecommerce.models.Order;
import com.adamo.ecommerce.payment.PaymentGateway;
import com.adamo.ecommerce.logging.entries.OrderPaymentFailedLog;
import com.adamo.ecommerce.metrics.entries.OrderWasPlacedMetric;
import com.adamo.ecommerce.repositories.ItemsRepository;
import com.adamo.ecommerce.repositories.OrdersRepository;
import com.adamo.ecommerce.web.requests.PlaceOrderRequest;
import com.adamo.ecommerce.web.responses.OrderConfirmationResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
public class OrdersController {


    private ItemsRepository itemsRepository;
    private OrdersRepository ordersRepository;
    private PaymentGateway paymentGateway;
    private Mailer mailer;
    private final Logger logger;
    private final Metrics metrics;

    public OrdersController(
            ItemsRepository itemsRepository,
            OrdersRepository ordersRepository,
            PaymentGateway paymentGateway,
            Mailer mailer,
            Logger logger,
            Metrics metrics
    ) {
        this.itemsRepository = itemsRepository;
        this.ordersRepository = ordersRepository;
        this.paymentGateway = paymentGateway;
        this.mailer = mailer;
        this.logger = logger;
        this.metrics = metrics;
    }

    @GetMapping("/orders/{orderId}")
    public OrderConfirmationResponse getOrderConfirmation(@PathVariable Integer orderId) {
        Optional<Order> found = ordersRepository.findById(orderId);
        Order order = found.get();

        Set<InventoryItem> inventory = order.getInventory();
        Item item = null;
        for (InventoryItem inventoryItem : inventory) {
            item = inventoryItem.getItem();
            break;
        }

        OrderConfirmationResponse response = new OrderConfirmationResponse();

        response.setCardLastFour(order.getCardLastFour());
        response.setEmail(order.getEmail());
        response.setItem(item.getName());
        response.setItemCost(item.getPrice());
        response.setItemQuantity(inventory.size());
        response.setTotalCost(item.getPrice() * inventory.size());

        return response;
    }

    @PostMapping("/orders")
    public void placeOrder(@RequestBody PlaceOrderRequest request) throws Exception {
        // get data from database
        Optional<Item> item = itemsRepository.findById(request.getItemId());
        Set<InventoryItem> inventory = item.get().getInventory();

        // validate there is enough inventory
        if (inventory.size() < request.getItemQuantity()) {
            throw new Exception("Inventory not enough");
        }
        // create an order
        Order order = ordersRepository.save(new Order(request.getEmail(), "pending", item.get().getPrice() * request.getItemQuantity()));

        // connect inventory items to order
        int amountUpdated = 0;
        for (InventoryItem inventoryItem : inventory) {
            inventoryItem.setOrder(order);
            if (amountUpdated++ >= request.getItemQuantity()) {
                break;
            }
        }

        // place payment with stripe
        try {
            Charge charge = this.paymentGateway.process(request.getPaymentToken(), order.getTotalCost());

            order.setStatus("payment_received");
            order.setCardLastFour(charge.getCardLastFour());
            ordersRepository.save(order);
        } catch (Exception e) {
            order.setStatus("payment_failed");
            ordersRepository.save(order);
            this.logger.log(new OrderPaymentFailedLog(order));
            throw new Exception("Payment failed.");
        }

        // send confirmation email
        mailer.sendMail(new OrderConfirmationEmail(order));
        metrics.add(new OrderWasPlacedMetric(order
        ));
    }
}
