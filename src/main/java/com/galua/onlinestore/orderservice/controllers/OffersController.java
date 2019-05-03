package com.galua.onlinestore.orderservice.controllers;

import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log
@RestController
public class OffersController {

    @GetMapping("offers/cat/{id}")
    public ResponseEntity<List<Integer>> getCategoryOfOffer(@PathVariable("id") int id) {
        return null;
    }

    @GetMapping("offers/price/{id}")
    public ResponseEntity<List<Integer>> getPriceOfOffer(@PathVariable("id") int id) {
        return null;
    }
}
