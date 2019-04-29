package com.galua.onlinestore.orderservice.controllers;

import com.galua.onlinestore.orderservice.entities.Status;
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
public class StatusController {
    @Autowired
    private StatusService statusService;

    @GetMapping("statuses/{id}")
    public ResponseEntity<Status> getStatusesById(@PathVariable("id") int id) {
        try {
            Status status = statusService.getStatusByID(id);
            return new ResponseEntity<>(status, HttpStatus.OK);
        }
        catch(NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("statuses")
    public ResponseEntity<List<Status>> getAllStatuses() {
        List<Status> list = statusService.getAllStatuses();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("statuses")
    public ResponseEntity addStatuses(@RequestBody Status status, UriComponentsBuilder builder) {
        try {
            statusService.createStatus(status);
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/statuses/{id}").buildAndExpand(status.getId()).toUri());
        return new ResponseEntity(status, headers, HttpStatus.CREATED);
    }

    @PutMapping("statuses")
    public ResponseEntity<Status> updateStatuses(@RequestBody Status status) {
        try {
            statusService.updateStatus(status);
            return new ResponseEntity<>(status, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("statuses/{id}")
    public ResponseEntity<Void> deleteStatuses(@PathVariable("id") int id) {
        try {
            statusService.deleteStatus(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
