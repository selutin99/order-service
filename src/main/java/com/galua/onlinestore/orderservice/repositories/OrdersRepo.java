package com.galua.onlinestore.orderservice.repositories;

import com.galua.onlinestore.orderservice.entities.Orders;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrdersRepo extends CrudRepository<Orders, Integer> {
    List<Orders> findByName(String name);

    List<Orders> findByPaid(boolean paid);
}
