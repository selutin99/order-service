package com.galua.onlinestore.orderservice.services;

import com.galua.onlinestore.orderservice.entities.Orders;

import java.util.List;

public interface OrdersService {
    void createOrder(Orders orders);
    void updateOrder(Orders orders);
    void deleteOrder(int id);

    Orders getOrderByID(int id);
    List<Orders> getAllOrders();
}
