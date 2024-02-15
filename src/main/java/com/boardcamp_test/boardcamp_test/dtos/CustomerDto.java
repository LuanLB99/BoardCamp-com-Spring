package com.boardcamp_test.boardcamp_test.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDto {
    
    @NotBlank(message = "Insira um nome de usuário")
    private String name;
    
    @NotBlank(message = "Insira um CPF")
    @Size(min = 11, max = 11, message = "O CPF deve conter 11 números")
    private String cpf;
}
