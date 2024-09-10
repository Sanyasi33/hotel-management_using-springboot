package com.dr.services;

import com.dr.entities.db.Customer;
import com.dr.entities.dto.CustomerDto;

import java.util.List;

public interface ICustomerService {

    //Create customer
    Customer createCustomer(CustomerDto customerDto);

    //Get customer
    Customer getCustomer(String userId);

    //Get all customer
    List<Customer> getAllCustomers();

    //Update customer
    Customer updateCustomer(String userId, CustomerDto customerDto);

    //Delete customer
    void deleteCustomer(String userId);
}
