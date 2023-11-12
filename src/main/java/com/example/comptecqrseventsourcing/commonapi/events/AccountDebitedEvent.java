package com.example.comptecqrseventsourcing.commonapi.events;

import com.example.comptecqrseventsourcing.commonapi.enums.AccountStatus;
import lombok.Getter;

public class AccountDebitedEvent extends BaseEvent<String> {
    @Getter private String currency;
    @Getter private double amount;
    @Getter private AccountStatus status;

    public AccountDebitedEvent(String id,String currency, double amount,AccountStatus status) {
        super(id);
        this.currency = currency;
        this.amount = amount;
        this.status=status;
    }
}
