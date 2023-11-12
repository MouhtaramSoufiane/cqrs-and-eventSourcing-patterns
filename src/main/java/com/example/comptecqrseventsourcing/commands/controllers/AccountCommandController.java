package com.example.comptecqrseventsourcing.commands.controllers;

import com.example.comptecqrseventsourcing.commonapi.commands.CreateAccountCommand;
import com.example.comptecqrseventsourcing.commonapi.commands.CreditAccountCommand;
import com.example.comptecqrseventsourcing.commonapi.commands.DebitAccountCommand;
import com.example.comptecqrseventsourcing.commonapi.dtos.CreateAccountRequestDTO;
import com.example.comptecqrseventsourcing.commonapi.dtos.CreditAccountRequestDTO;
import com.example.comptecqrseventsourcing.commonapi.dtos.DebitAccountRequestDTO;
import com.example.comptecqrseventsourcing.commonapi.enums.AccountStatus;
import lombok.AllArgsConstructor;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/commands/account")
@AllArgsConstructor
public class AccountCommandController {
    private  CommandGateway commandGateway;
    private EventStore eventStore;


    @PostMapping("/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountRequestDTO request){
        CompletableFuture<String> commandResponse = commandGateway.send(new CreateAccountCommand(UUID.randomUUID().toString(),
                request.getInitialBalance(),
                request.getCurrency()
        ));
        return commandResponse;
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/eventStore/{id}")
    public Stream eventStore(@PathVariable String id){

      return eventStore.readEvents(id).asStream();
    }
    @PostMapping("/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDTO request){
        CompletableFuture<String> commandResponse = commandGateway.send(new DebitAccountCommand(
                request.getAccountId(),
                request.getAmount(),
                request.getCurrency(),
                AccountStatus.CREATED
        ));
        return commandResponse;
    }
    @PostMapping("/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDTO request){
        CompletableFuture<String> commandResponse = commandGateway.send(new CreditAccountCommand(
                request.getAccountId(),
                request.getAmount(),
                request.getCurrency(),
                AccountStatus.CREATED
        ));
        return commandResponse;
    }


}
