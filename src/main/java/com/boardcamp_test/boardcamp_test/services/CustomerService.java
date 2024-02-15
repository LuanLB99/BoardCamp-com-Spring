package com.boardcamp_test.boardcamp_test.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.boardcamp_test.boardcamp_test.dtos.CustomerDto;
import com.boardcamp_test.boardcamp_test.exceptions.ConflictException;
import com.boardcamp_test.boardcamp_test.exceptions.NotFoundException;
import com.boardcamp_test.boardcamp_test.models.CustomerModel;
import com.boardcamp_test.boardcamp_test.repositories.CustomerRepository;

@Service
public class CustomerService {
    final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerModel> listAllCustomers(){
        return customerRepository.findAll();
    }

    public CustomerModel findCustomerById(Long id){
        Optional<CustomerModel> haveCustomer = customerRepository.findById(id);
        
        if(!haveCustomer.isPresent()){
            throw new NotFoundException("Usuário não encontrado!");
        }

        return haveCustomer.get();
    }

    public CustomerModel createCustomer(CustomerDto custormerDto){
        Optional<CustomerModel> haveCustomer = customerRepository.findByCpf(custormerDto.getCpf());
        if(haveCustomer.isPresent()){
            throw new ConflictException("Cpf já cadastrado!");
        }

        CustomerModel customer = new CustomerModel(custormerDto);
        customerRepository.save(customer);
        return customer;
    }
}