package com.boardcamp_test.boardcamp_test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boardcamp_test.boardcamp_test.models.RentalModel;

@Repository
public interface RentalRepository extends JpaRepository <RentalModel, Long> {
    
}
