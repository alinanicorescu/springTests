package com.example.demo.dao;
import java.math.BigDecimal;

/**
 * The model class for AccountBalance
 */
public class AccountBalance {

    private BigDecimal amount;

    public AccountBalance(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
