package com.adamo.ecommerce.models;

public class Charge {
    private Integer cardLastFour;

    public Charge(Integer cardLastFour) {
        this.cardLastFour = cardLastFour;
    }

    public Integer getCardLastFour() {
        return cardLastFour;
    }
}
