package com.galua.onlinestore.orderservice.services;

import com.galua.onlinestore.customerservice.entities.PaidType;
import com.galua.onlinestore.offerservice.entities.Categories;
import com.galua.onlinestore.offerservice.entities.Offers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private RestTemplate myRestTemplate;

    @Value("${service.customer.url}")
    private String serviceURL;

    public int getCustomerID(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer_"+token);

        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<Integer> response = myRestTemplate.exchange(
                serviceURL+"auth/customer",
                HttpMethod.GET,
                entity,
                Integer.class);
        return response.getBody();
    }
}
