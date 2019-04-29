package com.galua.onlinestore.orderservice.controllers;

import com.galua.onlinestore.orderservice.entities.Orders;
import com.galua.onlinestore.orderservice.services.OrdersService;
import com.galua.onlinestore.orderservice.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @Autowired
    private StatusService statusService;

    @GetMapping("orders/{id}")
    public ResponseEntity<Orders> getOrdersById(@PathVariable("id") int id) {
        Orders order = ordersService.getOrderByID(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("orders")
    public ResponseEntity<List<Orders>> getAllOrders() {
        List<Orders> list = ordersService.getAllOrders();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("orders")
    public ResponseEntity<Void> addOrders(@RequestBody Orders order, UriComponentsBuilder builder) {
        try {
            ordersService.createOrder(order);
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/orders/{id}").buildAndExpand(order.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("orders")
    public ResponseEntity<Orders> updateOrders(@RequestBody Orders order) {
        ordersService.updateOrder(order);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PatchMapping("orders/{id}")
    public ResponseEntity<Void> updateStatuses(@RequestBody Orders order,
                                               @PathVariable("id") int id) {
        if(order==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        order.setStatus(statusService.getStatusByID(id));
        ordersService.updateOrder(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("orders/{id}")
    public ResponseEntity<Void> deleteOrders(@PathVariable("id") int id) {
        ordersService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
