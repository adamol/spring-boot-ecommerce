package com.adamo.ecommerce.models;

import javax.persistence.*;

@Entity
@Table(name = "inventory_items")
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private String code;

    public InventoryItem() {}

    public
    InventoryItem(Item item) {
        this.item = item;
    }

    public InventoryItem(Item item, String code) {
        this.item = item;
        this.code = code;
    }

    public InventoryItem(Item item, Order order, String code) {
        this.item = item;
        this.order = order;
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
