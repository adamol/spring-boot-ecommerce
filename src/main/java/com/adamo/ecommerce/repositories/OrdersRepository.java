package com.adamo.ecommerce.repositories;

import com.adamo.ecommerce.models.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrdersRepository extends CrudRepository<Order, Integer> {
}
