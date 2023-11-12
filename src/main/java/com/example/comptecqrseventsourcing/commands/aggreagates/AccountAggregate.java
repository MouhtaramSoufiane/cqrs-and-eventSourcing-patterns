package com.example.comptecqrseventsourcing.commands.aggreagates;

import com.example.comptecqrseventsourcing.commonapi.commands.CreateAccountCommand;
import com.example.comptecqrseventsourcing.commonapi.commands.CreditAccountCommand;
import com.example.comptecqrseventsourcing.commonapi.commands.DebitAccountCommand;
import com.example.comptecqrseventsourcing.commonapi.dtos.CreditAccountRequestDTO;
import com.example.comptecqrseventsourcing.commonapi.enums.AccountStatus;
import com.example.comptecqrseventsourcing.commonapi.events.AccountCreatedEvent;
import com.example.comptecqrseventsourcing.commonapi.events.AccountCreditedEvent;
import com.example.comptecqrseventsourcing.commonapi.events.AccountDebitedEvent;
import com.example.comptecqrseventsourcing.commonapi.exceptions.BalanceNotSufficinetException;
import com.example.comptecqrseventsourcing.commonapi.exceptions.NegativeInitialBalanceException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private String currency;
    private double balance;
    private AccountStatus status;

    AccountAggregate(){
        // required by AXON FRAMEWORK
    }

    @CommandHandler
    AccountAggregate(CreateAccountCommand command) throws NegativeInitialBalanceException {
        if(command.getInitialBalance()<0){
            throw new NegativeInitialBalanceException("Negative balance");
        }
        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getId(),
                command.getCurrency(),
                command.getInitialBalance(),
                AccountStatus.CREATED
        ));
    }


    // Muter State of Application
    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        this.accountId=event.getId();
        this.balance=event.getBalance();
        this.status=event.getStatus();
        this.currency=event.getCurrency();
    }

    @CommandHandler
    public void handle(CreditAccountCommand command) throws NegativeInitialBalanceException {
        if(command.getAmount()<0){
            throw new NegativeInitialBalanceException("Negative amount");
        }

        AggregateLifecycle.apply(new AccountCreditedEvent(
                command.getId(),
                command.getCurrency(),
                command.getAmount(),
                command.getStatus()
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent event){
        this.accountId=event.getId();
        this.balance=this.balance+event.getAmount();
        this.currency=event.getCurrency();
    }
    @CommandHandler
    public void handle(DebitAccountCommand command) throws NegativeInitialBalanceException, BalanceNotSufficinetException {
        if(command.getAmount()<0){
            throw new NegativeInitialBalanceException("Negative amount");
        }
        if(command.getAmount()>this.balance){
            throw new BalanceNotSufficinetException("Balance not sufficinet");
        }

        AggregateLifecycle.apply(new AccountDebitedEvent(
                command.getId(),
                command.getCurrency(),
                command.getAmount(),
                command.getStatus()
        ));
    }
    @EventSourcingHandler
    public void on(AccountDebitedEvent event){
        this.accountId=event.getId();
        this.balance=this.balance-event.getAmount();
        this.currency=event.getCurrency();
    }

}
