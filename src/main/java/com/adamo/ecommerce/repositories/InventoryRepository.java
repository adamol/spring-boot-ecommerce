package com.adamo.ecommerce.repositories;

import com.adamo.ecommerce.models.InventoryItem;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface InventoryRepository extends CrudRepository<InventoryItem, Integer> {
    Set<InventoryItem> findByItemId(Integer id);
}
