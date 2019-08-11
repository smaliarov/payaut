package com.maliarov.payaut.web.service;

import com.maliarov.payaut.model.Account;
import com.maliarov.payaut.model.Transaction;
import com.maliarov.payaut.model.TransactionType;
import com.maliarov.payaut.repository.AccountRepository;
import com.maliarov.payaut.repository.TransactionRepository;
import com.maliarov.payaut.web.dto.AccountBalance;
import com.maliarov.payaut.web.dto.TransferBetweenAccounts;
import com.maliarov.payaut.web.dto.TransferSingleAccount;
import com.maliarov.payaut.web.dto.TransferBetweenAccountsResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.function.BiFunction;

@Service
public class TransferServiceImpl implements TransferService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransferServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public AccountBalance credit(String transactionId, TransferSingleAccount dto) {
        return transferSingle(transactionId, dto, TransactionType.CREDIT,
                (account, transferSingleAccount) -> account.getAmountInCents() + dto.getAmountInCents());
    }

    @Override
    @Transactional
    public AccountBalance debit(String transactionId, TransferSingleAccount dto) {
        return transferSingle(transactionId, dto, TransactionType.DEBIT,
                (account, transferSingleAccount) -> account.getAmountInCents() - dto.getAmountInCents());
    }

    private AccountBalance transferSingle(String transactionId, TransferSingleAccount dto, TransactionType transactionType,
                                          BiFunction<Account, TransferSingleAccount, Long> accountAmount) {
        return transactionRepository.findById(transactionId).or(() -> {
            Account account = getAccount(dto.getAccountId());

            account.setAmountInCents(accountAmount.apply(account, dto));

            Transaction newTransaction = new Transaction();
            newTransaction.setAccountTo(account);
            newTransaction.setAmountInCents(dto.getAmountInCents());
            newTransaction.setId(transactionId);
            newTransaction.setTransactionType(transactionType);

            accountRepository.save(account);

            return Optional.of(transactionRepository.save(newTransaction));
        }).map(Transaction::getAccountTo).map(ConvertorHelper::convertToBalance)
                //  normally, it should always exist, an empty transaction record would mean something went really weird
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not create new transaction"));
    }

    @Override
    @Transactional
    public TransferBetweenAccountsResult transferBetweenAccounts(String transactionId, TransferBetweenAccounts dto) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseGet(() -> {
            Account accountFrom = getAccount(dto.getAccountIdFrom());
            Account accountTo = getAccount(dto.getAccountIdTo());

            accountFrom.setAmountInCents(accountFrom.getAmountInCents() - dto.getAmountInCents());
            accountTo.setAmountInCents(accountTo.getAmountInCents() + dto.getAmountInCents());

            accountRepository.save(accountFrom);
            accountRepository.save(accountTo);

            Transaction newTransaction = new Transaction();
            newTransaction.setAccountFrom(accountFrom);
            newTransaction.setAccountTo(accountTo);
            newTransaction.setAmountInCents(dto.getAmountInCents());
            newTransaction.setId(transactionId);
            newTransaction.setTransactionType(TransactionType.TRANSFER);

            return transactionRepository.save(newTransaction);
        });

        return new TransferBetweenAccountsResult(
                ConvertorHelper.convertToBalance(transaction.getAccountFrom()),
                ConvertorHelper.convertToBalance(transaction.getAccountTo())
        );
    }

    private Account getAccount(String accountIdFrom) {
        return accountRepository.findById(accountIdFrom)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find account with id " + accountIdFrom));
    }
}
