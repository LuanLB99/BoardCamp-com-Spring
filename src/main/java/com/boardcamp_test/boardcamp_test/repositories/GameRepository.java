package com.boardcamp_test.boardcamp_test.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boardcamp_test.boardcamp_test.models.GameModel;

@Repository
public interface GameRepository extends JpaRepository <GameModel, Long>{
    public Optional<GameModel> findByName(String name);
} 