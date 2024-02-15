package com.boardcamp_test.boardcamp_test.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.boardcamp_test.boardcamp_test.dtos.GameDto;
import com.boardcamp_test.boardcamp_test.exceptions.ConflictException;
import com.boardcamp_test.boardcamp_test.models.GameModel;
import com.boardcamp_test.boardcamp_test.repositories.GameRepository;

@Service
public class GameService {
    final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<GameModel> listGames(){
        return gameRepository.findAll();
    }

    public GameModel createGame(GameDto gameForm){

        Optional <GameModel> haveGame = gameRepository.findByName(gameForm.getName());
        if(haveGame.isPresent()){
         throw new ConflictException("Nome do jogo já cadastrado!");
        }
        GameModel game = new GameModel(gameForm);
        gameRepository.save(game);
        return game;

    }
}