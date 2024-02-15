package com.boardcamp_test.boardcamp_test.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boardcamp_test.boardcamp_test.dtos.RentalDto;
import com.boardcamp_test.boardcamp_test.models.RentalModel;
import com.boardcamp_test.boardcamp_test.services.RentalService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rentals")

public class RentalController {
    final RentalService rentalService;
    
    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public ResponseEntity<Object> listAllRentals() {
        List<RentalModel> rentals =  rentalService.listRentals();
        return ResponseEntity.status(HttpStatus.OK).body(rentals);
    }
    

    @PostMapping
    public ResponseEntity<Object> createRental(@RequestBody @Valid RentalDto rentalDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            String errorMessage = "Erro de validação: " + bindingResult.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
       RentalModel rental = rentalService.createRental(rentalDto);

       return ResponseEntity.status(HttpStatus.CREATED).body(rental);
    }


    @PutMapping("/{id}/return")
    public ResponseEntity<Object> finishRent(@PathVariable Long id) {
       RentalModel rental = rentalService.updateRental(id);
        
        return ResponseEntity.status(HttpStatus.OK).body(rental);
    }

}