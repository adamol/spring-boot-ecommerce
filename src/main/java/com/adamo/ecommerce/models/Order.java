package com.adamo.ecommerce.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {

    public enum Status {
        PENDING, PAYMENT_RECEIVED, PAYMENT_CANCELLED, SHIPPED
    };

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToMany(mappedBy = "order")
    private Set<InventoryItem> inventory;

    private String email;

    // @TODO: change to enum
    private Status status;

    private Integer totalCost;

    private Integer cardLastFour;

    public Order() {}

    public Order(String email, Status status, Integer totalCost) {
        this.email = email;
        this.status = status;
        this.totalCost = totalCost;
    }

    public String getEmail() {
        return email;
    }

    public Status getStatus() {
        return status;
    }

    public Integer getTotalCost() {
        return totalCost;
    }

    public Integer getCardLastFour() {
        return cardLastFour;
    }

    public void setCardLastFour(Integer cardLastFour) {
        this.cardLastFour = cardLastFour;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public Set<InventoryItem> getInventory() {
        return inventory;
    }
}
