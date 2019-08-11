package com.maliarov.payaut.web.service;

import com.maliarov.payaut.web.dto.AccountBalanceDto;
import com.maliarov.payaut.web.dto.TransferBetweenAccountsDto;
import com.maliarov.payaut.web.dto.TransferSingleAccountDto;
import com.maliarov.payaut.web.dto.TransferBetweenAccountsResultDto;

public interface TransferService {
    AccountBalanceDto credit(String transactionId, TransferSingleAccountDto dto);

    AccountBalanceDto debit(String transactionId, TransferSingleAccountDto dto);

    TransferBetweenAccountsResultDto transferBetweenAccounts(String transactionId, TransferBetweenAccountsDto dto);
}
