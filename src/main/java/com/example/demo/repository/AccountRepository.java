package com.example.demo.repository;

import com.example.demo.dao.AccountOperationException;
import com.example.demo.dao.DataException;
import com.example.demo.repository.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by alinanicorescu on 02/07/2020.
 */

@Service
@Slf4j
public class AccountRepository {

    private Account account1;
    private  Account account2;
    private  Account account3;

    public AccountRepository() {
        try {
            this.account1 = new Account("user.test1@gmail.com", "acc1", BigDecimal.valueOf(100));
            this.account2 = new Account("user.test2@gmail.com", "acc2", BigDecimal.valueOf(400));
            this.account3 = new Account("user.test1@gmail.com", "acc2", BigDecimal.valueOf(400));
        } catch (AccountOperationException e) {
            log.error("Could not initialize accounts!");
        }
    }

    public  Optional<Account> getAccount(String email, String code)  {
        if (email.equals("user.test1@gmail.com") && code.equals("acc1")) {
            return  Optional.of(account1);
        }
        if (email.equals("user.test2@gmail.com") && code.equals("acc2")) {
            return  Optional.of(account2);
        }
        if (email.equals("user.test1@gmail.com") && code.equals("acc2")) {
            return Optional.of(account3);
        }
        log.error("Couldn't find account with email {} and code {}", email, code);
        return Optional.empty();
    }


    public void deposit(String email, String accountCode, BigDecimal amount) throws DataException, AccountOperationException {
        Optional<Account> account =  getAccount(email, accountCode);
        if (!account.isPresent()) {
            log.error("Couldn't find account with email {} and code {}", email, accountCode);
            throw new DataException("Nu user account found!");
        }
        Account userAccount = account.get();
        try {
            userAccount.deposit(amount);
        } catch (AccountOperationException e) {
            log.error("Could not deposit userEmail {} code {} amount {}", email, accountCode, amount);
            throw  e;
        }
    }

    public void transfer(String email, String fromAccount, String toAccount, BigDecimal amount)  throws DataException, AccountOperationException {
        Optional<Account> accountSource =  getAccount(email, fromAccount);
        Optional<Account> accountDest =  getAccount(email, toAccount);
        if (!(accountSource.isPresent()) || accountDest.isPresent()) {
            throw new DataException("Nu user account found!");
        }
        Account source = accountSource.get();
        Account dest = accountDest.get();
        try {
            source.blockAmount(amount);
            source.extract(amount, true);
            dest.deposit(amount);
        } catch (AccountOperationException e) {
            log.error("An error occurred while transfer from {} to {} userEmail {} amount {}",
                    fromAccount, toAccount, email, amount);
            throw e;
        }

    }
}
