package com.adamo.ecommerce.web;

import com.adamo.ecommerce.models.InventoryItem;
import com.adamo.ecommerce.repositories.InventoryRepository;
import com.adamo.ecommerce.models.Item;
import com.adamo.ecommerce.repositories.ItemsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ItemsCanBeViewed {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemsRepository itemsRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void clearDatabase() {
        this.itemsRepository.deleteAll();
    }

    @Test
    public void itemsCanBeViewed() throws Exception {
        ArrayList list = new ArrayList<Item>();
        list.add(this.itemsRepository.save(new Item("apple", 5)));
        list.add(this.itemsRepository.save(new Item("banana", 10)));

        this.mockMvc.perform(get("/items"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(this.objectMapper.writeValueAsString(list)));
    }

    @Test
    public void itemDetailsCanBeViewed() throws Exception {
        Item item = this.itemsRepository.save(new Item("apple", 5));
        inventoryRepository.save(new InventoryItem(item, "test12"));
        inventoryRepository.save(new InventoryItem(item, "test34"));

        this.mockMvc.perform(get("/items/" + item.getId()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("{\"id\":1,\"name\":\"apple\",\"price\":5,\"inventoryCount\":2}"));

        // assert also correct if one invnetory item has connection to order (later)
    }
}
