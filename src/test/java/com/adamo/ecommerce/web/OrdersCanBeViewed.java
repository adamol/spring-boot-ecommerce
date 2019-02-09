package com.adamo.ecommerce.web;

import com.adamo.ecommerce.models.InventoryItem;
import com.adamo.ecommerce.models.Item;
import com.adamo.ecommerce.models.Order;
import com.adamo.ecommerce.repositories.InventoryRepository;
import com.adamo.ecommerce.repositories.ItemsRepository;
import com.adamo.ecommerce.repositories.OrdersRepository;
import com.adamo.ecommerce.web.responses.OrderResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
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

    @Before
    public void clearDatabase() {
        this.itemsRepository.deleteAll();
    }

    @Test
    public void orderConfirmationCanBeViewed() throws Exception {
        Order order = new Order("john@example.com", Order.Status.PAYMENT_RECEIVED, 5);
        this.ordersRepository.save(order);
        Item item = new Item("apple", 5);
        itemsRepository.save(item);
        InventoryItem inventoryItem = new InventoryItem(item, order, "code12");
        inventoryRepository.save(inventoryItem);

        OrderResponse response = OrderResponse.create(order, item);

        this.mockMvc.perform(get("/orders/" + order.getId()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(this.objectMapper.writeValueAsString(response)));
    }

    @Test
    public void listOfOrdersCanBeViewed() throws Exception {
        Order orderA = new Order("john@example.com", Order.Status.PAYMENT_RECEIVED, 5);
        Order orderB = new Order("john@example.com", Order.Status.PAYMENT_RECEIVED, 5);
        Order orderC = new Order("john@example.com", Order.Status.PAYMENT_RECEIVED, 5);
        ordersRepository.save(orderA);
        ordersRepository.save(orderB);
        ordersRepository.save(orderC);
        Item item = new Item("apple", 5);
        itemsRepository.save(item);
        inventoryRepository.save(new InventoryItem(item, orderA, "code12"));
        inventoryRepository.save(new InventoryItem(item, orderB, "code34"));
        inventoryRepository.save(new InventoryItem(item, orderC, "code56"));

        OrderResponse responseA = OrderResponse.create(orderA, item);
        OrderResponse responseB = OrderResponse.create(orderB, item);
        OrderResponse responseC = OrderResponse.create(orderC, item);

        String expected = "[" + this.objectMapper.writeValueAsString(responseA) + "," + this.objectMapper.writeValueAsString(responseB) + "," + this.objectMapper.writeValueAsString(responseC) + "]";
        this.mockMvc.perform(get("/orders"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(expected));
    }
}
