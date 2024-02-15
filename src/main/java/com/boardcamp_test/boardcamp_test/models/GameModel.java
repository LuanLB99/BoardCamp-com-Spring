package com.boardcamp_test.boardcamp_test.models;

import java.math.BigDecimal;

import com.boardcamp_test.boardcamp_test.dtos.GameDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name="games")
public class GameModel {


    public GameModel(GameDto gameDto){
        this.name = gameDto.getName();
        this.image = gameDto.getImage();
        this.stockTotal = gameDto.getStockTotal();
        this.pricePerDay = gameDto.getPricePerDay();
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 380, nullable = false)
    private String image;

    @Column(nullable = false)
    private int stockTotal;

    @Column(nullable = false)
    private BigDecimal pricePerDay;

   public void setRentStockTotal(){
        this.stockTotal = getStockTotal() - 1;
    }

    public void setDevolutionStockTotal(){
        this.stockTotal = getStockTotal() + 1;
    }
}