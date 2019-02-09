package com.adamo.ecommerce.web.responses;

import com.adamo.ecommerce.models.Item;
import com.adamo.ecommerce.models.Order;

public class OrderResponse {

    private String email;

    private Integer cardLastFour;

    private String item;

    private Integer itemQuantity;

    private Integer itemCost;

    private Integer totalCost;

    public static OrderResponse create(Order order, Item item) {
        OrderResponse response = new OrderResponse();

        response.setCardLastFour(order.getCardLastFour());
        response.setEmail(order.getEmail());
        response.setItem(item.getName());
        response.setItemCost(item.getPrice());
        response.setItemQuantity(1);
        response.setTotalCost(item.getPrice() * 1);

        return response;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCardLastFour() {
        return cardLastFour;
    }

    public void setCardLastFour(Integer cardLastFour) {
        this.cardLastFour = cardLastFour;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public Integer getItemCost() {
        return itemCost;
    }

    public void setItemCost(Integer itemCost) {
        this.itemCost = itemCost;
    }

    public Integer getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }
}
