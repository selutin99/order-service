package com.galua.onlinestore.orderservice.services;

import com.galua.onlinestore.orderservice.entities.Orders;
import com.galua.onlinestore.orderservice.repositories.OrdersRepo;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log
@Service
public class OrdersServiceImpl implements OrdersService{
    @Autowired
    private OrdersRepo ordersRepository;

    @Override
    public void createOrder(Orders orders) {
        if(orders==null){
            log.severe("Был передан пустой заказ");
            throw new IllegalArgumentException("Заказ не передан");
        }
        List<Orders> list = ordersRepository.findByName(orders.getName());
        if (list.size() > 0) {
            log.severe("Был передан существующий заказ");
            throw new IllegalArgumentException("Заказ уже существует");
        }
        else {
            ordersRepository.save(orders);
        }
    }

    @Override
    public void updateOrder(Orders order) {
        ordersRepository.save(order);
    }

    @Override
    public void deleteOrder(int id) {
        ordersRepository.delete(getOrderByID(id));
    }

    @Override
    public Orders getOrderByID(int id) {
        return ordersRepository.findById(id).get();
    }

    @Override
    public List<Orders> getAllOrders() {
        List<Orders> listOfOrders = new ArrayList<>();
        ordersRepository.findAll().forEach(e -> listOfOrders.add(e));
        return listOfOrders;
    }
}
