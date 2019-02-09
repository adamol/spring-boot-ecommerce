package com.adamo.ecommerce.web;

import com.adamo.ecommerce.models.InventoryItem;
import com.adamo.ecommerce.repositories.InventoryRepository;
import com.adamo.ecommerce.models.Item;
import com.adamo.ecommerce.repositories.ItemsRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ItemsCanBeAdded {

    @Autowired
    private ItemsRepository itemsRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void clearDatabase() {
        this.itemsRepository.deleteAll();
    }

    @Test
    public void itemCanBeAdded() throws Exception {

        this.mockMvc.perform(
                    post("/items")
                            .content("{\"name\":\"test\",\"price\":12,\"inventoryCount\":3}")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print()).andExpect(status().isOk());

        Optional<Item> item = this.itemsRepository.findByName("test");
        Assert.assertTrue(item.isPresent());
        Assert.assertEquals((int) 12, (int) item.get().getPrice());

        // assert that there are 3 items in inventory
        // with codes generated
        Set<InventoryItem> inventory = inventoryRepository.findByItemId(item.get().getId());

        int counter = 0;
        for (InventoryItem inventoryItem : inventory) {
            counter++;
            Assert.assertNotNull(inventoryItem.getCode());
        }
        Assert.assertEquals(3, counter);

    }
}
