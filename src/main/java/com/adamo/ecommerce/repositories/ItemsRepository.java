package com.adamo.ecommerce.repositories;

import com.adamo.ecommerce.models.Item;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ItemsRepository extends CrudRepository<Item, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Item SET name = :name, price = :price WHERE id = :itemId")
    public void update(@Param("itemId") Integer itemId, @Param("name") String name, @Param("price") Integer price);

    public Optional<Item> findByName(String name);
}
