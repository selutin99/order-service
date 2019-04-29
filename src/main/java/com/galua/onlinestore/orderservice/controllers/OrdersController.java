package com.galua.onlinestore.orderservice.controllers;

import com.galua.onlinestore.orderservice.entities.Orders;
import com.galua.onlinestore.orderservice.entities.Status;
import com.galua.onlinestore.orderservice.services.OrdersService;
import com.galua.onlinestore.orderservice.services.StatusService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.NoSuchElementException;

@Log
@RestController
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

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
            ordersService.updateOrder(id, order);
            log.severe("Заказ обновлён успешно");
            order.setId(id);
            return new ResponseEntity<>(order, HttpStatus.OK);
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
    public ResponseEntity updateStatuses(@PathVariable("id") int id,
                                         @RequestBody Status status) {
        if(status==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            ordersService.updateStatus(id, status);
        }
        catch(NoSuchElementException e){
            log.severe("Заказ не найден");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            log.severe("Передан неверный заказ");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(status, HttpStatus.OK);
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
