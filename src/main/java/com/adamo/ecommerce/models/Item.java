package com.adamo.ecommerce.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private Integer price;

    @OneToMany(mappedBy = "item")
    private Set<InventoryItem> inventory;

    public Item() {}

    public Item(String name, Integer price) {
        this.name = name;
        this.price = price;
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

    public Set<InventoryItem> getInventory() {
        return inventory;
    }
}
