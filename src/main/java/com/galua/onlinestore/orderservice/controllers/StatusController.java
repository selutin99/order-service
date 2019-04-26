package com.galua.onlinestore.orderservice.controllers;

import com.galua.onlinestore.orderservice.entities.Status;
import com.galua.onlinestore.orderservice.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class StatusController {
    @Autowired
    private StatusService statusService;

    @GetMapping("status/{id}")
    public ResponseEntity<Status> getStatusById(@PathVariable("id") int id) {
        Status status = statusService.getStatusByID(id);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @GetMapping("statuses")
    public ResponseEntity<List<Status>> getAllStatuses() {
        List<Status> list = statusService.getAllStatuses();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("status")
    public ResponseEntity<Void> addStatus(@RequestBody Status status, UriComponentsBuilder builder) {
        try {
            statusService.createStatus(status);
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/status/{id}").buildAndExpand(status.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("status")
    public ResponseEntity<Status> updateStatus(@RequestBody Status status) {
        statusService.updateStatus(status);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @DeleteMapping("status/{id}")
    public ResponseEntity<Void> deleteStatus(@PathVariable("id") int id) {
        statusService.deleteStatus(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
