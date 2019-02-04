package com.adamo.ecommerce.web;

import com.adamo.ecommerce.domain.Item;
import com.adamo.ecommerce.domain.ItemsRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ItemsController {

    private ItemsRepository itemsRepository;

    public ItemsController(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    @GetMapping("/items")
    public Iterable<Item> listItems() {
        Iterable<Item> items = itemsRepository.findAll();

        return items;
    }

    @GetMapping("/items/{itemId}")
    public Optional<Item> getItem(@PathVariable Integer itemId) {
        Optional<Item> item = itemsRepository.findById(itemId);

        return item;
    }

    @PostMapping("/items")
    public Item saveItem(@RequestBody Item item) {
        Item saved = itemsRepository.save(item);

        return saved;
    }

    @PutMapping("/items/{itemId}")
    public Optional<Item> updateItem(@PathVariable Integer itemId, @RequestBody Item item) {
        itemsRepository.update(itemId, item.getName(), item.getPrice());

        return itemsRepository.findById(itemId);
    }

    @DeleteMapping("/items/{itemId}")
    public void deleteItem(@PathVariable Integer itemId) {
        itemsRepository.deleteById(itemId);
    }
}
