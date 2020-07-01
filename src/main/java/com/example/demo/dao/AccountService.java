package com.example.demo.dao;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Service class for account operations
 */
@Service
public class AccountService {

    public Optional<AccountBalance> getAccountBalance(String email, String accountCode) throws DataException {
        return Optional.of(new AccountBalance(new BigDecimal(100)));
    }

    public void deposit(String email, String accountCode, BigDecimal amount) throws AccountOperationException {
    }

    public void transfer(String email, String fromAccount, String toAccount, BigDecimal amount)  throws AccountOperationException {

    }
}
