package com.boardcamp_test.boardcamp_test.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameDto {
    
    @NotBlank(message = "Insira o nome do jogo")
    private String name;

    private String image;

    @Positive(message = "O número em estoque disponível deve ser maior que zero.")
    private int stockTotal;

    @Positive(message = "O preço por dia deve ser maior que zero.")
    private BigDecimal PricePerDay;
}