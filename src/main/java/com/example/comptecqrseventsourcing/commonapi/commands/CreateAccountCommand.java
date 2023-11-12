package com.example.comptecqrseventsourcing.commonapi.commands;

import com.example.comptecqrseventsourcing.commonapi.enums.AccountStatus;
import lombok.Getter;

public class CreateAccountCommand extends BaseCommand<String>{

  @Getter private double initialBalance;
  @Getter private String currency;

    public CreateAccountCommand(String id,double initialBalance,String currency) {
        super(id);
        this.currency=currency;
        this.initialBalance=initialBalance;

    }
}
