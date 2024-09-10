package com.dr.services;

import com.dr.entities.db.Customer;
import com.dr.entities.dto.CustomerDto;
import com.dr.entities.dto.RatingDto;
import com.dr.exceptions.ResourceNotFoundException;
import com.dr.feigns.RatingFeign;
import com.dr.repositories.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private RatingFeign ratingFeign;

    @Override
    public Customer createCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto, customer);
        String randomId = UUID.randomUUID().toString();
        customer.setCustomerId(randomId);
        return customerRepo.save(customer);
    }

    @Override   //Getting Ratings using FeignClient ***************************
    public Customer getCustomer(String customerId) {
        Optional<Customer> opt = customerRepo.findById(customerId);
        if (opt.isPresent()){
            Customer customer=opt.get();
            // Get all ratings from external api
            List<RatingDto> ratingDtos = ratingFeign.getAllRatingsByCustomerId(customerId);
            customer.setRatings(ratingDtos);
            return customer;
        }
        throw new ResourceNotFoundException("Customer not found with id: " + customerId);
    }
    /*@Override //Without Ratings ***************************
    public Customer getCustomer(String customerId) {
        return customerRepo.findById(customerId).orElseThrow(ResourceNotFoundException::new);
    }*/
    /*@Override //Getting Ratings using RestTemplate ***************************
    public Customer getCustomer(String customerId) {
        Optional<Customer> opt = customerRepo.findById(customerId);
        if (opt.isPresent()){
            Customer customer = opt.get();
            // Create RestTemplate object to call external apis
            RestTemplate template = new RestTemplate();
            // Get all ratings from external api
            Rating[] ratings = template.getForObject("http://localhost:8083/rating/customer/" + customerId, Rating[].class);
            customer.setRatings(List.of(ratings));
            return customer;
        }
        throw new ResourceNotFoundException("Customer not found with id: " + customerId);
    }*/

    @Override   //Getting Ratings using FeignClient ***************************
    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepo.findAll();
        /*for(Customer customer: customers){
            List<Rating> ratings = ratingFeign.getAllRatingsByCustomerId(customer.getCustomerId());
            customer.setRatings(ratings);
        }*/
        customers.forEach(customer->customer.setRatings(ratingFeign.getAllRatingsByCustomerId(customer.getCustomerId())));
        return customers;
    }
    /*@Override //Without Ratings ***************************
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }*/
    /*@Override //Getting Ratings using RestTemplate ***************************
    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepo.findAll();
        RestTemplate template = new RestTemplate();
        for(Customer customer: customers){
            // Get all ratings from external api
            Rating[] ratings = template.getForObject("http://localhost:8083/rating/customer/" + customer.getCustomerId(), Rating[].class);
            customer.setRatings(List.of(ratings));
        }
        return customers;
    }*/

    @Override
    public Customer updateCustomer(String customerId, CustomerDto customerDto) {
        Optional<Customer> opt = customerRepo.findById(customerId);
        if (opt.isPresent()){
            Customer existingCustomer = opt.get();
            BeanUtils.copyProperties(customerDto, existingCustomer);
            return customerRepo.save(existingCustomer);
        }
        throw new ResourceNotFoundException();
    }

    @Override
    public void deleteCustomer(String customerId) {
        Optional<Customer> opt = customerRepo.findById(customerId);
        if (opt.isPresent()){
            customerRepo.delete(opt.get());
        }
        else {
            throw new ResourceNotFoundException("Customer not found with id: " + customerId);
        }
    }
}
