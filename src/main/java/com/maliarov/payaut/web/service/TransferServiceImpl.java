package com.maliarov.payaut.web.service;

import com.maliarov.payaut.web.dto.AccountBalanceDto;
import com.maliarov.payaut.web.dto.TransferBetweenAccountsDto;
import com.maliarov.payaut.web.dto.TransferSingleAccountDto;
import com.maliarov.payaut.web.dto.TransferBetweenAccountsResultDto;
import org.springframework.stereotype.Service;

@Service
public class TransferServiceImpl implements TransferService {
    @Override
    public AccountBalanceDto credit(String transactionId, TransferSingleAccountDto dto) {
        return null;
    }

    @Override
    public AccountBalanceDto debit(String transactionId, TransferSingleAccountDto dto) {
        return null;
    }

    @Override
    public TransferBetweenAccountsResultDto transferBetweenAccounts(String transactionId, TransferBetweenAccountsDto dto) {
        return null;
    }
}
