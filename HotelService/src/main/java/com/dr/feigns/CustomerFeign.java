package com.dr.feigns;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("CUSTOMERSERVICE")
public interface CustomerFeign {

// Call CustomerService's API to get customer details (This api & method present in CustomerService project)
    @GetMapping("/customer/{customerId}")
    void getCustomer(@PathVariable String customerId);
    //Above method return type is Customer class in Customer service,
    //but I don't need the returned value, so I've declared void type

    //I need this method to check whether it's raising exception or not
    //If Exception raised then customerId is invalid or else customerId is valid.
}
