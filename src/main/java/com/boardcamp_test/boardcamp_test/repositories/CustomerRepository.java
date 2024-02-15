package com.boardcamp_test.boardcamp_test.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boardcamp_test.boardcamp_test.models.CustomerModel;

public interface CustomerRepository extends JpaRepository <CustomerModel, Long>{
    public Optional<CustomerModel> findByCpf(String cpf);
}
