package com.dr.controllers;

import com.dr.entities.db.Customer;
import com.dr.entities.dto.CustomerDto;
import com.dr.services.CustomerService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService userService;

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerDto customerDto){
        Customer customer = userService.createCustomer(customerDto);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}")
//This Api is calling RatingService Api, So I annotated CircuitBreaker to handle failures from RatingService
    @CircuitBreaker(name = "RatingService", fallbackMethod = "ratingFallbackMethod")
    public ResponseEntity<Customer> getCustomer(@PathVariable String customerId){
        Customer customer = userService.getCustomer(customerId);
        return ResponseEntity.ok(customer);
    }
//Fallback Method
    public ResponseEntity<Customer> ratingFallbackMethod(@PathVariable String customerId, Exception e){
        Customer customer =new Customer();
        customer.setCustomerId("Dummy123");
        customer.setName("Dummy");
        customer.setEmail("dummy@gmail.com");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(customer);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers(){
        List<Customer> customers = userService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String customerId, @RequestBody CustomerDto customerDto){
        Customer customer = userService.updateCustomer(customerId, customerDto);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String customerId){
        userService.deleteCustomer(customerId);
        //return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User Deleted Successfully");
        //return new ResponseEntity<>("User Deleted Successfully", HttpStatus.NO_CONTENT);
        /*HttpStatus.NO_CONTENT is not allowed the body. So the message is not showing*/
        return new ResponseEntity<>("Customer deleted successfully", HttpStatus.OK);
    }
}
