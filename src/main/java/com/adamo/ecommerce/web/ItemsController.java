package com.adamo.ecommerce.web;

import com.adamo.ecommerce.models.InventoryItem;
import com.adamo.ecommerce.models.InventoryItemCode;
import com.adamo.ecommerce.models.Item;
import com.adamo.ecommerce.repositories.InventoryRepository;
import com.adamo.ecommerce.repositories.ItemsRepository;
import com.adamo.ecommerce.web.requests.AddItemRequest;
import com.adamo.ecommerce.web.responses.ItemDetailsResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class
ItemsController {

    private ItemsRepository itemsRepository;

    private InventoryRepository inventoryRepository;

    public ItemsController(ItemsRepository itemsRepository, InventoryRepository inventoryRepository) {
        this.itemsRepository = itemsRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @GetMapping("/items")
    public Iterable<Item> listItems() {
        Iterable<Item> items = itemsRepository.findAll();

        return items;
    }

    @GetMapping("/items/{itemId}")
    public ItemDetailsResponse getItem(@PathVariable Integer itemId) {
        Optional<Item> item = itemsRepository.findById(itemId);

        return ItemDetailsResponse.fromItem(item.get());
    }

    @PostMapping("/items")
    public Item saveItem(@RequestBody AddItemRequest request) {
        Item item = new Item(request.getName(), request.getPrice());
        Item saved = itemsRepository.save(item);

        for (int i = 0; i < request.getInventoryCount(); i++) {
            InventoryItemCode code = InventoryItemCode.create();
            InventoryItem inventoryItem = new InventoryItem(saved, code.toString());
            inventoryRepository.save(inventoryItem);
        }

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
