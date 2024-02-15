package com.boardcamp_test.boardcamp_test.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RentalDto {
    
    @NotNull(message = "Insira o ID do usuário desejado!")
    private Long customerId;

    @NotNull(message = "Insira o ID do jogo desejado!")
    private Long gameId;

    @Positive(message = "Insira um valor positivo para a quantidade de dias alugados!")
    private Long daysRented;
}