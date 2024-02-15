package com.boardcamp_test.boardcamp_test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.boardcamp_test.boardcamp_test.dtos.CustomerDto;
import com.boardcamp_test.boardcamp_test.models.CustomerModel;
import com.boardcamp_test.boardcamp_test.repositories.CustomerRepository;
import com.boardcamp_test.boardcamp_test.repositories.RentalRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerIntegrationTests {
    
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @BeforeEach
    void cleanUpDataBase(){
        rentalRepository.deleteAll();
        customerRepository.deleteAll();

    }


    @Test
	void givenReapeatedCPF_whenCreatingCustomer_ThenThrowsError(){
        //given
        String cpf = "01234567843";
		CustomerDto customerDto = new CustomerDto("Josias", cpf);
		CustomerModel customer = new CustomerModel(customerDto);
        CustomerModel savedCustomer = customerRepository.save(customer);

        CustomerDto newUserDto = new CustomerDto("Joel", cpf);
        HttpEntity body = new HttpEntity<>(newUserDto);
        //when
        ResponseEntity<String> response = restTemplate.exchange("/customers", HttpMethod.POST, body, String.class);
        //then
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(1, customerRepository.count());
    }



    @Test
    void givenValidCPF_whenCreatingCustomer_thenCreatesCustomer(){

        CustomerDto newCustomer = new CustomerDto("otavio", "03234567843");
        HttpEntity<CustomerDto> body = new HttpEntity<>(newCustomer);

        ResponseEntity<CustomerModel> response = restTemplate.exchange("/customers", HttpMethod.POST, body, CustomerModel.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, customerRepository.count());
    }



}