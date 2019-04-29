package com.galua.onlinestore.orderservice.services;

import com.galua.onlinestore.orderservice.entities.Orders;
import com.galua.onlinestore.orderservice.entities.Status;
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
    public void createOrder(Orders order) {
        if(order==null){
            log.severe("Был передан пустой заказ");
            throw new IllegalArgumentException("Заказ не передан");
        }
        List<Orders> list = ordersRepository.findByName(order.getName());
        if (list.size() > 0) {
            log.severe("Был передан существующий заказ");
            throw new IllegalArgumentException("Заказ уже существует");
        }
        else {
            ordersRepository.save(order);
            log.severe("Сохранение заказа: "+order);
        }
    }

    @Override
    public void updateStatus(int id, Status status){
        Orders findOrder = getOrderByID(id);
        findOrder.setStatus(status);
        ordersRepository.save(findOrder);
        log.severe("Обновление статуса: "+findOrder);
    }

    @Override
    public void updateOrder(int id, Orders order) {
        Orders findOrder = getOrderByID(id);

        findOrder.setOfferID(order.getOfferID());
        findOrder.setName(order.getName());
        findOrder.setDeliveryTime(order.getDeliveryTime());
        findOrder.setStatus(order.getStatus());
        findOrder.setCustomerID(order.getCustomerID());
        findOrder.setPaid(order.isPaid());

        createOrder(findOrder);
        log.severe("Обновление заказа: "+findOrder);
    }

    @Override
    public void deleteOrder(int id) {
        log.severe("Удаление заказа с id="+id);
        ordersRepository.delete(getOrderByID(id));
    }

    @Override
    public Orders getOrderByID(int id) {
        log.severe("Получение заказа с id="+id);
        return ordersRepository.findById(id).get();
    }

    @Override
    public List<Orders> getAllOrders() {
        log.severe("Получение всех заказов");
        List<Orders> listOfOrders = new ArrayList<>();
        ordersRepository.findAll().forEach(e -> listOfOrders.add(e));
        return listOfOrders;
    }
}
