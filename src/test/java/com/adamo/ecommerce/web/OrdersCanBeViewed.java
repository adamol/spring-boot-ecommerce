package com.adamo.ecommerce.web;

import com.adamo.ecommerce.models.InventoryItem;
import com.adamo.ecommerce.models.Item;
import com.adamo.ecommerce.models.Order;
import com.adamo.ecommerce.repositories.InventoryRepository;
import com.adamo.ecommerce.repositories.ItemsRepository;
import com.adamo.ecommerce.repositories.OrdersRepository;
import com.adamo.ecommerce.web.responses.OrderConfirmationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OrdersCanBeViewed {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    ItemsRepository itemsRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void orderConfirmationCanBeViewed() throws Exception {
        Order order = new Order("john@example.com", "payment_received", 5);
        this.ordersRepository.save(order);
        Item item = new Item("apple", 5);
        itemsRepository.save(item);
        InventoryItem inventoryItem = new InventoryItem(item, order, "code12");
        inventoryRepository.save(inventoryItem);

        OrderConfirmationResponse response = new OrderConfirmationResponse();

        response.setCardLastFour(order.getCardLastFour());
        response.setEmail(order.getEmail());
        response.setItem(item.getName());
        response.setItemCost(item.getPrice());
        response.setItemQuantity(1);
        response.setTotalCost(item.getPrice() * 1);

        this.mockMvc.perform(get("/orders/" + order.getId()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(this.objectMapper.writeValueAsString(response)));
    }

    public void listOfOrdersCanBeViewed() {

    }
}
