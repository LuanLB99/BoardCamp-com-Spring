package com.boardcamp_test.boardcamp_test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.boardcamp_test.boardcamp_test.dtos.GameDto;
import com.boardcamp_test.boardcamp_test.models.GameModel;
import com.boardcamp_test.boardcamp_test.repositories.GameRepository;
import com.boardcamp_test.boardcamp_test.repositories.RentalRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GameIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private GameRepository gameRepository;


    @BeforeEach
    void cleanUpDataBase(){
        rentalRepository.deleteAll();
        gameRepository.deleteAll();

    }

    @Test
    void givenValidGame_WhenCreatingGame_thenCreatesGame(){
        int intValue = 10;
        BigDecimal fromLong =new BigDecimal(4500);
        GameDto game = new GameDto("Crash Bandcook", "crashImage", intValue, fromLong);
        HttpEntity<GameDto> body = new HttpEntity<>(game);

        ResponseEntity<GameModel> response = restTemplate.exchange("/games", HttpMethod.POST, body, GameModel.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, gameRepository.count());
    }
}