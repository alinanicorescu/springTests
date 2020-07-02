package com.example.demo.repository;

import com.example.demo.dao.AccountOperationException;

import java.math.BigDecimal;
/**
 * Created by alinanicorescu on 02/07/2020.
 */
public class Account {

    private String ownerEmail;

    private  String code;

    private BigDecimal total;

    private BigDecimal blockedAmount;

    private BigDecimal minimumAmount = BigDecimal.ZERO;


    public Account(String userEmail, String code,  BigDecimal initialAmount) throws AccountOperationException {
        if (initialAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new AccountOperationException("Could not open account with negative amount");
        }
        this.ownerEmail = userEmail;
        this.total = initialAmount;
        this.code = code;
    }

    public BigDecimal getBalance() {
        if (blockedAmount != null) {
            return total.subtract(blockedAmount);
        } else {
            return total;
        }
    }

    public void blockAmount(BigDecimal amount) throws AccountOperationException {
        BigDecimal remainder = getBalance().subtract(amount);
        if (remainder.compareTo(minimumAmount) > 0) {
            total = remainder;
            if (blockedAmount != null) {
                blockedAmount = blockedAmount.add(amount);
            } else {
                blockedAmount = minimumAmount;
            }
        } else {
            throw new AccountOperationException("Not available");
        }
    }

    public synchronized void deposit(BigDecimal amount) throws AccountOperationException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AccountOperationException("Tryied to deposit negative value");
        }
        total = total.add(amount);
    }

    public synchronized void extract(BigDecimal amount, Boolean blocked) throws AccountOperationException {
        if (blocked == true) {
            if (blockedAmount == null) {
                throw new AccountOperationException("Not enough blocked!");
            }
            if (blockedAmount.compareTo(amount) > 0) {
                blockedAmount = blockedAmount.subtract(amount);
            } else {
                throw new AccountOperationException("Not enough balance to extract amount");
            }
        } else {
                if (getBalance().compareTo(amount) > 0) {
                    total = total.subtract(amount);
                } else {
                    throw new AccountOperationException("Not enough balance to extract amount");
                }
            }
    }
}
