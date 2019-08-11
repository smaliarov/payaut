package com.maliarov.payaut.web.service;

import com.maliarov.payaut.model.Account;
import com.maliarov.payaut.repository.AccountRepository;
import com.maliarov.payaut.web.dto.AccountBalanceDto;
import com.maliarov.payaut.web.dto.AccountCreateDto;
import com.maliarov.payaut.web.dto.AccountDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto create(AccountCreateDto input) {
        // very simple validation
        if (StringUtils.isEmpty(input.getOwner())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Owner cannot be empty");
        }

        Account account = new Account();
        account.setOwner(input.getOwner());
        account.setAmountInCents(0L);

        account = accountRepository.save(account);

        return convert(account);
    }

    @Override
    public AccountBalanceDto getBalance(String accountId) {
        return accountRepository.findById(accountId).map(this::convert).map(this::convert)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find account with id " + accountId));
    }

    private AccountDto convert(Account account) {
        AccountDto result = new AccountDto();

        result.setId(account.getId());
        result.setAmountInCents(account.getAmountInCents());
        result.setOwner(account.getOwner());

        return result;
    }

    private AccountBalanceDto convert(AccountDto account) {
        AccountBalanceDto result = new AccountBalanceDto();

        result.setId(account.getId());
        result.setAmountInCents(account.getAmountInCents());

        return result;
    }
}
