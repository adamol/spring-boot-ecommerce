package com.adamo.ecommerce.web.requests;

public class AddItemRequest {

    private String name;

    private Integer price;

    private Integer inventoryCount;

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
