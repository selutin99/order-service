package com.galua.onlinestore.orderservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OffersService {

    @Autowired
    private RestTemplate myRestTemplate;

    @Value("${service.offer.url}")
    private String restUrl;

    public int getPrices(int id) {
        return myRestTemplate.getForObject(restUrl+id, Integer.class);
    }
}
