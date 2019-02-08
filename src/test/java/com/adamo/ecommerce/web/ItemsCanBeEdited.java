package com.adamo.ecommerce.web;

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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ItemsCanBeEdited {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemsRepository itemsRepository;

    @Before
    public void clearDatabase() {
        this.itemsRepository.deleteAll();
    }

    @Test
    public void itemsCanBeUpdated() throws Exception {
        Item inserted = this.itemsRepository.save(new Item("apple", 10));

        this.mockMvc.perform(
                    put("/items/" + inserted.getId())
                        .content("{\"name\":\"apple\",\"price\":5}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print()).andExpect(status().isOk());

        Optional<Item> found = this.itemsRepository.findById(inserted.getId());
        Assert.assertTrue(found.isPresent());
        Assert.assertEquals((int) 5, (int) found.get().getPrice());
    }

    @Test
    public void itemsCanBeDeleted() throws Exception {
        Item inserted = this.itemsRepository.save(new Item("apple", 10));

        this.mockMvc.perform(
                    delete("/items/" + inserted.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print()).andExpect(status().isOk());

        Optional<Item> found = this.itemsRepository.findById(inserted.getId());
        Assert.assertFalse(found.isPresent());
    }
}
