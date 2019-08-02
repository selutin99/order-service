package com.galua.onlinestore.orderservice.controllers;

import com.galua.onlinestore.offerservice.entities.Categories;
import com.galua.onlinestore.orderservice.services.OffersService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@Log
@RestController
public class OffersController {

    @Autowired
    OffersService offersService;

    @GetMapping("offers/category/{id}")
    public ResponseEntity<Categories> getCategoryOfOffer(@PathVariable("id") int id) {
        try {
            Categories cat = offersService.getCategory(id);
            log.severe("Категория найдена успешно");
            return new ResponseEntity<>(cat, HttpStatus.OK);
        }
        catch(NoSuchElementException e){
            log.severe("Оффер не найден");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("offers/price/{id}")
    public ResponseEntity<Float> getPriceOfOffer(@PathVariable("id") int id) {
        try {
            float price = offersService.getPrice(id);
            log.severe("Цена найдена успешно");
            return new ResponseEntity<>(price, HttpStatus.OK);
        }
        catch(NoSuchElementException e){
            log.severe("Оффер не найден");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
