package com.example.comptecqrseventsourcing.commonapi.exceptions;

public class NegativeInitialBalanceException extends Exception {
    public NegativeInitialBalanceException(String message) {
        super(message);
    }
}
