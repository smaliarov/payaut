package com.maliarov.payaut.web.service;

import com.maliarov.payaut.web.dto.AccountBalance;
import com.maliarov.payaut.web.dto.AccountCreate;
import com.maliarov.payaut.web.dto.AccountFull;

public interface AccountService {
    AccountFull create(AccountCreate input);

    AccountBalance getBalance(String accountId);
}
