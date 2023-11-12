package com.example.comptecqrseventsourcing.query.services;

import com.example.comptecqrseventsourcing.commonapi.enums.TransactionType;
import com.example.comptecqrseventsourcing.commonapi.events.AccountCreatedEvent;
import com.example.comptecqrseventsourcing.commonapi.events.AccountCreditedEvent;
import com.example.comptecqrseventsourcing.commonapi.events.AccountDebitedEvent;
import com.example.comptecqrseventsourcing.query.entities.Account;
import com.example.comptecqrseventsourcing.query.entities.AccountTransaction;
import com.example.comptecqrseventsourcing.query.queries.GetAllAccounts;
import com.example.comptecqrseventsourcing.query.repostory.AccountRepository;
import com.example.comptecqrseventsourcing.query.repostory.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class AccountEventHandlerService {
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    @EventHandler
    public void on(AccountCreatedEvent event, EventMessage<AccountCreatedEvent> eventMessage){
        log.info("**************************************************");
        log.info("AccountRepository Received");
        Account account=new Account();
        account.setId(event.getId());
        account.setBalance(event.getBalance());
        account.setStatus(event.getStatus());
        account.setCurrency(event.getCurrency());
        account.setCreatedAt(eventMessage.getTimestamp());



        accountRepository.save(account);

    }
    @EventHandler
    public void on(AccountCreditedEvent event, EventMessage<AccountCreatedEvent> eventMessage){
        log.info("**************************************************");
        log.info("AccountRepository Received");

        Account account = accountRepository.findById(event.getId()).get();

        account.setBalance(account.getBalance()+event.getAmount());
        account.setStatus(event.getStatus());
        account.setCurrency(event.getCurrency());
        account.setCreatedAt(eventMessage.getTimestamp());
        accountRepository.save(account);

        AccountTransaction transaction=new AccountTransaction();

        transaction.setAccount(account);
        transaction.setTransactionType(TransactionType.CREDIT);
        transaction.setAmount(event.getAmount());
        transaction.setTimestamp(eventMessage.getTimestamp());
        transactionRepository.save(transaction);





    }
    @EventHandler
    public void on(AccountDebitedEvent event, EventMessage<AccountCreatedEvent> eventMessage){
        log.info("**************************************************");
        log.info("AccountRepository Received");

        Account account = accountRepository.findById(event.getId()).get();

        account.setBalance(account.getBalance()-event.getAmount());
        account.setStatus(event.getStatus());
        account.setCurrency(event.getCurrency());
        account.setCreatedAt(eventMessage.getTimestamp());

        accountRepository.save(account);
        AccountTransaction transaction=new AccountTransaction();

        transaction.setAccount(account);
        transaction.setTransactionType(TransactionType.DEBIT);
        transaction.setAmount(event.getAmount());
        transaction.setTimestamp(eventMessage.getTimestamp());
        transactionRepository.save(transaction);

    }

    @QueryHandler
    public List<Account> on(GetAllAccounts query){
        return accountRepository.findAll();

    }
}
