package com.example.comptecqrseventsourcing.commonapi.dtos;

import com.example.comptecqrseventsourcing.commonapi.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CreateAccountRequestDTO {
    private double initialBalance;
    private String currency;
}
