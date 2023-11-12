package com.example.comptecqrseventsourcing.query.repostory;

import com.example.comptecqrseventsourcing.query.entities.Account;
import com.example.comptecqrseventsourcing.query.entities.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<AccountTransaction,Long> {
}
