package com.galua.onlinestore.orderservice.controllers;

import com.galua.onlinestore.offerservice.entities.Offers;
import com.galua.onlinestore.orderservice.services.CustomerService;
import com.galua.onlinestore.orderservice.entities.Orders;
import com.galua.onlinestore.orderservice.services.OrdersService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Log
@RestController
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("orders/{id}")
    public ResponseEntity<Orders> getOrdersById(@PathVariable("id") int id) {
        try {
            Orders status = ordersService.getOrderByID(id);
            log.severe("Заказ найден успешно");
            return new ResponseEntity<>(status, HttpStatus.OK);
        }
        catch(NoSuchElementException e){
            log.severe("Заказ не найден");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("orders/make")
    public ResponseEntity<Orders> makeOrders(@RequestBody Map<Object, Object> request) {
        try {
            int customerID = this.customerService.getCustomerID(request.get("token").toString());
            LinkedHashMap offer = (LinkedHashMap) request.get("offer");

            //Создаём новый заказ
            Orders order = new Orders();

            order.setName(offer.get("name").toString());
            order.setDeliveryTime(new Timestamp(System.currentTimeMillis()));
            order.setStatus(null);
            order.setCustomerID(customerID);
            order.setOfferID(Integer.valueOf(offer.get("id").toString()));
            order.setPaid(false);

            ordersService.createOrder(order);

            log.severe("Заказ создан");
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        }
        catch(Exception e){
            log.severe("При оформлении заказа произошла ошибка");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("orders")
    public ResponseEntity<List<Orders>> getAllOrders() {
        List<Orders> list = ordersService.getAllOrders();
        log.severe("Получены все заказы");
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("orders")
    public ResponseEntity addOrders(@RequestBody Orders order, UriComponentsBuilder builder) {
        try {
            ordersService.createOrder(order);
        }
        catch(IllegalArgumentException e){
            log.severe("Попытка добавления существующего заказа");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch(Exception e){
            log.severe("Передан неверный заказ");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/orders/{id}").buildAndExpand(order.getId()).toUri());
        log.severe("Заказ добавлен успешно");
        return new ResponseEntity(order, headers, HttpStatus.CREATED);
    }

    @PutMapping("orders/{id}")
    public ResponseEntity<Orders> updateOrders(@PathVariable(value = "id") int id,
                                               @RequestBody Orders order) {
        try {
            Orders findOrder = ordersService.updateOrder(id, order);
            log.severe("Заказ обновлён успешно");
            return new ResponseEntity<>(findOrder, HttpStatus.OK);
        }
        catch(NoSuchElementException e){
            log.severe("Передан несуществующий заказ");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            log.severe("Передан неверный заказ");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("orders/{id}")
    public ResponseEntity updateStatuses(@PathVariable("id") int orderID,
                                         @RequestBody int statusID) {
        try {
            Orders findOrder = ordersService.updateStatus(orderID, statusID);
            return new ResponseEntity(findOrder, HttpStatus.OK);
        }
        catch(NoSuchElementException e){
            log.severe("Заказ/статус не найден");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            log.severe("Передан неверный заказ/статус");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("orders/{id}")
    public ResponseEntity<Void> deleteOrders(@PathVariable("id") int id) {
        try {
            ordersService.deleteOrder(id);
            log.severe("Заказ удалён успешно");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(NoSuchElementException e){
            log.severe("Заказ не найден");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
