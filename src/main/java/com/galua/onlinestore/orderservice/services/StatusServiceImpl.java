package com.galua.onlinestore.orderservice.services;

import com.galua.onlinestore.orderservice.entities.Status;
import com.galua.onlinestore.orderservice.repositories.StatusRepo;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log
@Service
public class StatusServiceImpl implements StatusService{

    @Autowired
    private StatusRepo statusRepositoty;

    @Override
    public void createStatus(Status status) {
        if(status==null){
            log.severe("Был передан пустой статус");
            throw new IllegalArgumentException("Статус не передан");
        }
        List<Status> list = statusRepositoty.findByName(status.getName());
        if (list.size() > 0) {
            log.severe("Был передан существующий статус");
            throw new IllegalArgumentException("Статус уже существует");
        }
        else {
            statusRepositoty.save(status);
        }
    }

    @Override
    public void updateStatus(Status status) { statusRepositoty.save(status); }

    @Override
    public void deleteStatus(int id) {
        statusRepositoty.delete(getStatusByID(id));
    }

    @Override
    public Status getStatusByID(int id) { return statusRepositoty.findById(id).get(); }

    @Override
    public List<Status> getAllStatuses() {
        List<Status> listOfStatuses = new ArrayList<>();
        statusRepositoty.findAll().forEach(e -> listOfStatuses.add(e));
        return listOfStatuses;
    }
}
