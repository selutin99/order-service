package com.galua.onlinestore.orderservice.repositories;

import com.galua.onlinestore.orderservice.entities.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusRepo extends CrudRepository<Status, Integer>{
    List<Status> findByName(String name);
}
