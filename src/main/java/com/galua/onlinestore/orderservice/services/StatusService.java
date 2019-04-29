package com.galua.onlinestore.orderservice.services;

import com.galua.onlinestore.orderservice.entities.Status;

import java.util.List;

public interface StatusService {
    void createStatus(Status status);
    void updateStatus(int id, Status status);
    void deleteStatus(int id);

    Status getStatusByID(int id);
    List<Status> getAllStatuses();
}
