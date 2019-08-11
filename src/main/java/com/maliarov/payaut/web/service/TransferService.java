package com.maliarov.payaut.web.service;

import com.maliarov.payaut.web.dto.AccountBalance;
import com.maliarov.payaut.web.dto.TransferBetweenAccounts;
import com.maliarov.payaut.web.dto.TransferSingleAccount;
import com.maliarov.payaut.web.dto.TransferBetweenAccountsResult;

public interface TransferService {
    AccountBalance credit(String transactionId, TransferSingleAccount dto);

    AccountBalance debit(String transactionId, TransferSingleAccount dto);

    TransferBetweenAccountsResult transferBetweenAccounts(String transactionId, TransferBetweenAccounts dto);
}
