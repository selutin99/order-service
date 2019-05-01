package com.galua.onlinestore.orderservice.services;

import com.galua.onlinestore.orderservice.entities.Orders;

import java.util.List;

public interface OrdersService {
    void createOrder(Orders orders);
    Orders updateOrder(int id, Orders orders);
    Orders updateStatus(int orderID, int statusID);
    void deleteOrder(int id);

    Orders getOrderByID(int id);
    List<Orders> getAllOrders();
}
