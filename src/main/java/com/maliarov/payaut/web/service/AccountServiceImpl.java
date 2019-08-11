package com.maliarov.payaut.web.service;

import com.maliarov.payaut.model.Account;
import com.maliarov.payaut.repository.AccountRepository;
import com.maliarov.payaut.web.dto.AccountBalance;
import com.maliarov.payaut.web.dto.AccountCreate;
import com.maliarov.payaut.web.dto.AccountFull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public AccountFull create(AccountCreate input) {
        // very simple validation
        if (StringUtils.isEmpty(input.getOwner())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Owner cannot be empty");
        }

        Account account = new Account();
        account.setOwner(input.getOwner());
        account.setAmountInCents(0L);

        account = accountRepository.save(account);

        return ConvertorHelper.convertToFull(account);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountBalance getBalance(String accountId) {
        return accountRepository.findById(accountId).map(ConvertorHelper::convertToBalance)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find account with id " + accountId));
    }
}
