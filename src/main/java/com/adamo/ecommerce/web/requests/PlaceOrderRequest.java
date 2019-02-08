package com.adamo.ecommerce.web.requests;

public class PlaceOrderRequest {

    private String email;

    private Integer itemId;

    private Integer itemQuantity;

    private String paymentToken;

    public PlaceOrderRequest(String email, Integer itemId, Integer itemQuantity, String paymentToken) {
        this.email = email;
        this.itemId = itemId;
        this.itemQuantity = itemQuantity;
        this.paymentToken = paymentToken;
    }

    public String getEmail() {
        return email;
    }

    public Integer getItemId() {
        return itemId;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public String getPaymentToken() {
        return paymentToken;
    }
}
