package com.maliarov.payaut.web.service;

import com.maliarov.payaut.model.Account;
import com.maliarov.payaut.web.dto.AccountBalance;
import com.maliarov.payaut.web.dto.AccountFull;

class ConvertorHelper {
    private ConvertorHelper() {
    }

    static AccountFull convertToFull(Account account) {
        AccountFull result = new AccountFull();

        result.setId(account.getId());
        result.setAmountInCents(account.getAmountInCents());
        result.setOwner(account.getOwner());

        return result;
    }

    static AccountBalance convertToBalance(Account account) {
        AccountBalance result = new AccountBalance();

        result.setId(account.getId());
        result.setAmountInCents(account.getAmountInCents());

        return result;
    }
}
