package com.example.demo.dao;

/**
 * Exception class for Database errors
 */
public class DataException extends Exception {
    public DataException(String errorMessage) {
        super(errorMessage);
    }
}
