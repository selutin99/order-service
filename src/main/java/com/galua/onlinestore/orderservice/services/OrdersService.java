package com.galua.onlinestore.orderservice.services;

import com.galua.onlinestore.orderservice.entities.Orders;
import com.galua.onlinestore.orderservice.entities.Status;

import java.util.List;

public interface OrdersService {
    void createOrder(Orders orders);
    void updateOrder(int id, Orders orders);
    void updateStatus(int id, Status status);
    void deleteOrder(int id);

    Orders getOrderByID(int id);
    List<Orders> getAllOrders();
}
