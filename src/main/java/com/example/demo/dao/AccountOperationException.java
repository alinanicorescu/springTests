package com.example.demo.dao;

/**
 * Created by alinanicorescu on 01/07/2020.
 */
public class AccountOperationException extends Exception {
    public AccountOperationException(String errorMessage) {
        super(errorMessage);
    }
}
