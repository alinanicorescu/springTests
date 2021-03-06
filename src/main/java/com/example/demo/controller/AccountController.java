package com.example.demo.controller;

import com.example.demo.dao.AccountService;
import com.example.demo.dao.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.*;
import lombok.extern.slf4j.Slf4j;


/**
 * Created by alinanicorescu on 01/07/2020.
 */
@RestController
@RequestMapping("/account")
@Slf4j
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping(value = "{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BigDecimal getAccountBalance(@PathVariable("email") String email,
                                            @RequestParam("accountCode") String accountCode)  {
        log.trace("getAccountBalance for {}, {}", email, accountCode);
        try {
           return accountService.getAccountBalance(email, accountCode);
        } catch (DataException e) {
            log.debug("No account found for user email {} and account code: {}", email, accountCode, e.getMessage());
            throw new ResponseStatusException(NOT_FOUND, "Account Not Found");
        }
    }


    @PostMapping(value = "deposit",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void deposit(@RequestParam("email") String email,
                        @RequestParam("accountCode") String accountCode,
                        @RequestParam("amount") BigDecimal amount) {
        log.trace("deposit for email %s, account code %s, amount %d",
                email, accountCode, amount);
        try {
            accountService.deposit(email, accountCode, amount);
        } catch (Exception e) {
            log.error(
                    String.format("Error while deposit for user email: %s  account code: %s amount: %d",
                            email, accountCode, amount), e);
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "The operation could not be completed");
        }
    }


    @PostMapping(value = "transfer", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void transfer(@RequestParam("email") String email,
                         @RequestParam("from") String fromAccount,
                         @RequestParam("to") String toAccount,
                         @RequestParam("amount") BigDecimal amount) {
        log.trace("transfer for email %s, fromAccount %s toAccount %s amount %d", email,
                fromAccount, toAccount, amount);
        try {
            accountService.transfer(email, fromAccount, toAccount, amount);
        } catch (Exception e) {
            log.error(
                    String.format("Error while transfer for user email: %s from account: %s to account: %s amount %d",
                            email, fromAccount, toAccount, amount), e);
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "The operation could not be completed");
        }
    }
}


