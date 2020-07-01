package com.example.demo.dao;

/**
 * Custom exception class for account operation failure
 */
public class AccountOperationException extends Exception {
    public AccountOperationException(String errorMessage) {
        super(errorMessage);
    }
}
