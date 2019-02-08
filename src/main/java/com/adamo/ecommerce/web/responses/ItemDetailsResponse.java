package com.adamo.ecommerce.web.responses;

import com.adamo.ecommerce.models.Item;

public class ItemDetailsResponse {
    private Integer id;

    private String name;

    private Integer price;

    private Integer inventoryCount;

    public ItemDetailsResponse(Integer id, String name, Integer price, Integer inventoryCount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.inventoryCount = inventoryCount;
    }

    public static ItemDetailsResponse fromItem(Item item) {
        return new ItemDetailsResponse(
            item.getId(),
            item.getName(),
            item.getPrice(),
            item.getInventory().size()
        );
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getInventoryCount() {
        return inventoryCount;
    }
}
