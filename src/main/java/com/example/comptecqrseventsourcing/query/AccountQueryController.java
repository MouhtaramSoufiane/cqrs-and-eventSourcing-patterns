package com.example.comptecqrseventsourcing.query;

import com.example.comptecqrseventsourcing.query.entities.Account;
import com.example.comptecqrseventsourcing.query.queries.GetAllAccounts;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/query/account")
public class AccountQueryController {
    private QueryGateway queryGateway;

    public AccountQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/list")
    public List<Account> accountList() throws ExecutionException, InterruptedException {
        CompletableFuture<List<Account>> query = queryGateway.query(new GetAllAccounts(), ResponseTypes.multipleInstancesOf(Account.class));
        List<Account> accounts = query.get();
        return accounts;
    }
}
