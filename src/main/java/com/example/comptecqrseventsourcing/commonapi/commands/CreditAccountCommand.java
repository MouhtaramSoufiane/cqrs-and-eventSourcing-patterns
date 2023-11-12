package com.example.comptecqrseventsourcing.commonapi.commands;

import com.example.comptecqrseventsourcing.commonapi.enums.AccountStatus;
import lombok.Getter;

public class CreditAccountCommand extends BaseCommand<String>{
    @Getter private double amount;
    @Getter private String currency;
    @Getter private AccountStatus status;

    public CreditAccountCommand(String id, double amount, String currency,AccountStatus status) {
        super(id);
        this.currency=currency;
        this.amount=amount;
        this.status=status;
    }
}
