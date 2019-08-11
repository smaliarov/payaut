package com.maliarov.payaut.web.dto;

public class TransferBetweenAccountsResultDto {
    private AccountBalanceDto accountBalanceFrom;
    private AccountBalanceDto accountBalanceTo;

    public AccountBalanceDto getAccountBalanceFrom() {
        return accountBalanceFrom;
    }

    public void setAccountBalanceFrom(AccountBalanceDto accountBalanceFrom) {
        this.accountBalanceFrom = accountBalanceFrom;
    }

    public AccountBalanceDto getAccountBalanceTo() {
        return accountBalanceTo;
    }

    public void setAccountBalanceTo(AccountBalanceDto accountBalanceTo) {
        this.accountBalanceTo = accountBalanceTo;
    }
}
