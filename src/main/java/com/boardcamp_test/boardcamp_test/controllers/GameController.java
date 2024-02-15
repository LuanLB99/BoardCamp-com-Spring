package com.boardcamp_test.boardcamp_test.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boardcamp_test.boardcamp_test.dtos.GameDto;
import com.boardcamp_test.boardcamp_test.models.GameModel;
import com.boardcamp_test.boardcamp_test.services.GameService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/games")

public class GameController {
    final GameService gameService;

    
    
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }


    @GetMapping
    public ResponseEntity<Object> listGames(){
        List<GameModel> games = gameService.listGames();
        return ResponseEntity.status(HttpStatus.OK).body(games);
    }

    @PostMapping()
    public ResponseEntity<Object> createGame(@RequestBody @Valid GameDto gameForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            String errorMessage = "Erro de validação: " + bindingResult.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
        GameModel game = gameService.createGame(gameForm);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(game);
    }
    
}