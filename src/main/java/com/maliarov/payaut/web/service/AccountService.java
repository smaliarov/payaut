package com.maliarov.payaut.web.service;

import com.maliarov.payaut.web.dto.AccountBalanceDto;
import com.maliarov.payaut.web.dto.AccountCreateDto;
import com.maliarov.payaut.web.dto.AccountDto;

public interface AccountService {
    AccountDto create(AccountCreateDto input);

    AccountBalanceDto getBalance(String accountId);
}
