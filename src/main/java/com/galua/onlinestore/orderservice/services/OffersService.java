package com.galua.onlinestore.orderservice.services;

import com.galua.onlinestore.offerservice.entities.Categories;
import com.galua.onlinestore.offerservice.entities.Offers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OffersService {

    @Autowired
    private RestTemplate myRestTemplate;

    @Value("${service.offer.url}")
    private String serviceURL;

    public float getPrice(int id) {
        Offers offer = myRestTemplate.getForObject(serviceURL+"offers/"+id, Offers.class);
        return offer.getPrice();
    }

    public Categories getCategory(int id){
        Offers offer = myRestTemplate.getForObject(serviceURL+"offers/"+id, Offers.class);
        return offer.getCategory();
    }
}
