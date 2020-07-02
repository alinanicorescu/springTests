package com.example.demo.dao;

import com.example.demo.repository.Account;
import com.example.demo.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Service class for account operations
 */
@Service
@Slf4j
public class AccountService {

    @Autowired
    private AccountRepository repository;

    public BigDecimal getAccountBalance(String email, String accountCode) throws DataException {
        Optional<Account> account = repository.getAccount(email, accountCode);
        if (!account.isPresent()) {
            throw new DataException("Account not found!");
        } else {
            return account.get().getBalance();
        }
    }

    public void deposit(String email, String accountCode, BigDecimal amount) throws AccountOperationException, DataException {
        repository.deposit(email, accountCode, amount);
    }

    public void transfer(String email, String fromAccount, String toAccount, BigDecimal amount)  throws AccountOperationException, DataException {
        repository.transfer(email, fromAccount, toAccount, amount);
    }
}
