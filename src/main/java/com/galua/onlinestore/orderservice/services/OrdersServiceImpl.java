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

    @Autowired
    private StatusService statusService;

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
            if(order.getStatus()!=null) {
                order.setStatus(statusService.getStatusByID(order.getStatus().getId()));
            }
            ordersRepository.save(order);
            log.severe("Сохранение заказа: "+order);
        }
    }

    @Override
    public Orders updateStatus(int orderID, int statusID){
        Orders findOrder = getOrderByID(orderID);
        findOrder.setStatus(statusService.getStatusByID(statusID));
        ordersRepository.save(findOrder);
        log.severe("Обновление статуса: "+findOrder);
        return findOrder;
    }

    @Override
    public Orders updateOrder(int id, Orders order) {
        Orders findOrder = getOrderByID(id);

        findOrder.setOfferID(order.getOfferID());
        findOrder.setName(order.getName());
        findOrder.setDeliveryTime(order.getDeliveryTime());
        findOrder.setStatus(statusService.getStatusByID(order.getStatus().getId()));
        findOrder.setCustomerID(order.getCustomerID());
        findOrder.setPaid(order.isPaid());

        List<Orders> list = ordersRepository.findByName(order.getName());
        if(order.getName().equals(findOrder.getName())) {
            list.remove(findOrder);
        }
        if(list.size()>0){
            throw new IllegalArgumentException("Заказ уже существует");
        }
        ordersRepository.save(findOrder);
        log.severe("Обновление заказа: "+findOrder);
        return findOrder;
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
