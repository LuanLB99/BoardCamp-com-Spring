package com.boardcamp_test.boardcamp_test.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boardcamp_test.boardcamp_test.dtos.CustomerDto;
import com.boardcamp_test.boardcamp_test.models.CustomerModel;
import com.boardcamp_test.boardcamp_test.services.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")

public class CustomerController {

    final CustomerService customerService;
    
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<Object> listCustomers() {
        List<CustomerModel> customersList = customerService.listAllCustomers();
        return ResponseEntity.status(HttpStatus.OK).body(customersList);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getCustomerById(@PathVariable Long id) {
        CustomerModel customer = customerService.findCustomerById(id);

        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }

    @PostMapping
    public ResponseEntity<Object> createCustomer(@RequestBody @Valid CustomerDto customerForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            String errorMessage = "Erro de validação: " + bindingResult.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        CustomerModel customer = customerService.createCustomer(customerForm);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }
    
}