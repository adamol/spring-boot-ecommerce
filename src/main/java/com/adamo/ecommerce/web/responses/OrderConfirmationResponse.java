package com.adamo.ecommerce.web.responses;

public class OrderConfirmationResponse {

    private String email;

    private Integer cardLastFour;

    private String item;

    private Integer itemQuantity;

    private Integer itemCost;

    private Integer totalCost;

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
