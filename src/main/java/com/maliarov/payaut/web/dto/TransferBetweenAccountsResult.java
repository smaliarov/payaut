package com.maliarov.payaut.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Result of the transfer between two accounts")
public class TransferBetweenAccountsResult {
    @ApiModelProperty(notes = "Balance of the account from which money were transferred", required = true)
    private AccountBalance accountBalanceFrom;
    @ApiModelProperty(notes = "Balance of the account to which money were transferred", required = true)
    private AccountBalance accountBalanceTo;

    public TransferBetweenAccountsResult() {
    }

    public TransferBetweenAccountsResult(AccountBalance accountBalanceFrom, AccountBalance accountBalanceTo) {
        this.accountBalanceFrom = accountBalanceFrom;
        this.accountBalanceTo = accountBalanceTo;
    }

    public AccountBalance getAccountBalanceFrom() {
        return accountBalanceFrom;
    }

    public void setAccountBalanceFrom(AccountBalance accountBalanceFrom) {
        this.accountBalanceFrom = accountBalanceFrom;
    }

    public AccountBalance getAccountBalanceTo() {
        return accountBalanceTo;
    }

    public void setAccountBalanceTo(AccountBalance accountBalanceTo) {
        this.accountBalanceTo = accountBalanceTo;
    }
}
