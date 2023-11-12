package com.example.comptecqrseventsourcing.commonapi.dtos;

import com.example.comptecqrseventsourcing.commonapi.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class DebitAccountRequestDTO {
    private String accountId;
    private String currency;
    private double amount;

}
