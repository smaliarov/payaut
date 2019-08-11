package com.maliarov.payaut.integration;

import com.maliarov.payaut.model.Account;
import com.maliarov.payaut.web.dto.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for the whole workflow
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FullBookkeepingTest extends AbstractTestNGSpringContextTests {
    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @LocalServerPort
    private int port;

    private String firstAccountId;
    private String secondAccountId;

    public FullBookkeepingTest() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(1000);
        requestFactory.setReadTimeout(1000);

        restTemplate.getRestTemplate().setRequestFactory(requestFactory);
    }

    @Test(groups = "create")
    public void createTooShortName() {
        AccountCreate input = new AccountCreate();
        input.setOwner(RandomStringUtils.randomAlphanumeric(2));

        ResponseEntity<Object> entity = restTemplate.postForEntity(getAccountUrl(""), input, Object.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test(groups = "create")
    public void createTooLongName() {
        AccountCreate input = new AccountCreate();
        input.setOwner(RandomStringUtils.randomAlphanumeric(256));

        ResponseEntity<Object> entity = restTemplate.postForEntity(getAccountUrl(), input, Object.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }


    @Test(groups = "create")
    public void create() {
        String owner = RandomStringUtils.randomAlphanumeric(128);

        AccountCreate input = new AccountCreate();
        input.setOwner(owner);

        ResponseEntity<AccountFull> entity = restTemplate.postForEntity(getAccountUrl(), input, AccountFull.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        AccountFull firstAccount = entity.getBody();
        assertThat(firstAccount).isNotNull();
        assertThat(firstAccount.getOwner()).isEqualTo(owner);
        assertThat(firstAccount.getAmountInCents()).isEqualTo(0L);
        firstAccountId = firstAccount.getId();
        assertThat(firstAccountId).isNotBlank();

        entity = restTemplate.postForEntity(getAccountUrl(), input, AccountFull.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        AccountFull secondAccount = entity.getBody();
        assertThat(secondAccount).isNotNull();
        assertThat(secondAccount.getOwner()).isEqualTo(owner);
        assertThat(secondAccount.getAmountInCents()).isEqualTo(0L);
        secondAccountId = secondAccount.getId();
        assertThat(secondAccountId).isNotBlank();
    }

    @Test(groups = "balance", dependsOnGroups = "create")
    public void balanceNonExisting() {
        ResponseEntity<Object> entity = restTemplate.getForEntity(getAccountUrl("/" + UUID.randomUUID().toString() + "/balance"), Object.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test(groups = "balance", dependsOnGroups = "create")
    public void balanceUppercase() {
        ResponseEntity<Object> entity = restTemplate.getForEntity(getAccountUrl("/" + firstAccountId.toUpperCase() + "/balance"), Object.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test(groups = "balance", dependsOnGroups = "create")
    public void balanceFirst() {
        ResponseEntity<AccountBalance> entity = restTemplate.getForEntity(getAccountUrl("/" + firstAccountId + "/balance"), AccountBalance.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        AccountBalance accountBalance = entity.getBody();
        assertThat(accountBalance).isNotNull();
        assertThat(accountBalance.getAmountInCents()).isEqualTo(0L);
    }

    @Test(groups = "transferSingle", dependsOnGroups = "balance")
    public void creditNonExisting() throws URISyntaxException {
        transferSingleNonExisting("credit");
    }

    @Test(groups = "transferSingle", dependsOnGroups = "balance")
    public void debitNonExisting() throws URISyntaxException {
        transferSingleNonExisting("debit");
    }

    @Test(groups = "transferSingle", dependsOnGroups = "balance")
    public void creditNegative() throws URISyntaxException {
        transferSingleNegative("credit", firstAccountId);
    }

    @Test(groups = "transferSingle", dependsOnGroups = "balance")
    public void debitNegative() throws URISyntaxException {
        transferSingleNegative("debit", secondAccountId);
    }

    @Test(groups = "transferSingle", dependsOnGroups = "balance")
    public void credit() throws URISyntaxException {
        transferSingle("credit", firstAccountId, 50000L);
    }

    @Test(groups = "transferSingle", dependsOnGroups = "balance")
    public void debit() throws URISyntaxException {
        transferSingle("debit", secondAccountId, -50000L);
    }

    private void transferSingle(String operation, String accountId, Long expectedAmount) throws URISyntaxException {
        TransferSingleAccount input = new TransferSingleAccount();
        input.setAccountId(accountId);
        input.setAmountInCents(50000L);

        String transactionId = UUID.randomUUID().toString();

        ResponseEntity<AccountBalance> entity = this.restTemplate.exchange(
                new RequestEntity<>(input, HttpMethod.PATCH, new URI(getTransferUrl(transactionId, "/" + operation))), AccountBalance.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        AccountBalance accountBalance = entity.getBody();
        assertThat(accountBalance).isNotNull();
        assertThat(accountBalance.getAmountInCents()).isEqualTo(expectedAmount);

        // same transaction second time
        entity = this.restTemplate.exchange(
                new RequestEntity<>(input, HttpMethod.PATCH, new URI(getTransferUrl(transactionId, "/" + operation))), AccountBalance.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        accountBalance = entity.getBody();
        assertThat(accountBalance).isNotNull();
        assertThat(accountBalance.getAmountInCents()).isEqualTo(expectedAmount);
    }

    private void transferSingleNonExisting(String operation) throws URISyntaxException {
        TransferSingleAccount input = new TransferSingleAccount();
        input.setAccountId(UUID.randomUUID().toString());
        input.setAmountInCents(50000L);

        ResponseEntity<Object> entity = this.restTemplate.exchange(
                new RequestEntity<>(input, HttpMethod.PATCH, new URI(getTransferUrl(UUID.randomUUID().toString(), "/" + operation))), Object.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private void transferSingleNegative(String operation, String accountId) throws URISyntaxException {
        TransferSingleAccount input = new TransferSingleAccount();
        input.setAccountId(accountId);
        input.setAmountInCents(-50000L);

        ResponseEntity<Object> entity = this.restTemplate.exchange(
                new RequestEntity<>(input, HttpMethod.PATCH, new URI(getTransferUrl(UUID.randomUUID().toString(), "/" + operation))), Object.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test(groups = "transferBetweenAccounts", dependsOnGroups = "transferSingle")
    public void transferBetweenAccountsNonExistingFrom() throws URISyntaxException {
        TransferBetweenAccounts input = new TransferBetweenAccounts();
        input.setAccountIdFrom(UUID.randomUUID().toString());
        input.setAccountIdTo(secondAccountId);
        input.setAmountInCents(50000L);

        ResponseEntity<Object> entity = this.restTemplate.exchange(
                new RequestEntity<>(input, HttpMethod.PATCH, new URI(getTransferUrl(UUID.randomUUID().toString()))), Object.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test(groups = "transferBetweenAccounts", dependsOnGroups = "transferSingle")
    public void transferBetweenAccountsNonExistingTo() throws URISyntaxException {
        TransferBetweenAccounts input = new TransferBetweenAccounts();
        input.setAccountIdFrom(firstAccountId);
        input.setAccountIdTo(UUID.randomUUID().toString());
        input.setAmountInCents(50000L);

        ResponseEntity<Object> entity = this.restTemplate.exchange(
                new RequestEntity<>(input, HttpMethod.PATCH, new URI(getTransferUrl(UUID.randomUUID().toString()))), Object.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test(groups = "transferBetweenAccounts", dependsOnGroups = "transferSingle")
    public void transferBetweenAccountsNegative() throws URISyntaxException {
        TransferBetweenAccounts input = new TransferBetweenAccounts();
        input.setAccountIdFrom(firstAccountId);
        input.setAccountIdTo(secondAccountId);
        input.setAmountInCents(-50000L);

        ResponseEntity<Object> entity = this.restTemplate.exchange(
                new RequestEntity<>(input, HttpMethod.PATCH, new URI(getTransferUrl(UUID.randomUUID().toString(), "/"))), Object.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test(groups = "transferBetweenAccounts", dependsOnGroups = "transferSingle")
    public void transferBetweenAccounts() throws URISyntaxException {
        TransferBetweenAccounts input = new TransferBetweenAccounts();
        input.setAccountIdFrom(firstAccountId);
        input.setAccountIdTo(secondAccountId);
        input.setAmountInCents(20000L);

        String transactionId = UUID.randomUUID().toString();

        transferBetweenAccounts(input, transactionId);

        // same transaction second time
        transferBetweenAccounts(input, transactionId);
    }

    private void transferBetweenAccounts(TransferBetweenAccounts input, String transactionId) throws URISyntaxException {
        ResponseEntity<TransferBetweenAccountsResult> entity = this.restTemplate.exchange(
                new RequestEntity<>(input, HttpMethod.PATCH, new URI(getTransferUrl(transactionId))), TransferBetweenAccountsResult.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        TransferBetweenAccountsResult result = entity.getBody();
        assertThat(result).isNotNull();

        AccountBalance accountBalanceFrom = result.getAccountBalanceFrom();
        assertThat(accountBalanceFrom).isNotNull();
        assertThat(accountBalanceFrom.getId()).isEqualTo(firstAccountId);
        assertThat(accountBalanceFrom.getAmountInCents()).isEqualTo(30000L);

        AccountBalance accountBalanceTo = result.getAccountBalanceTo();
        assertThat(accountBalanceTo).isNotNull();
        assertThat(accountBalanceTo.getId()).isEqualTo(secondAccountId);
        assertThat(accountBalanceTo.getAmountInCents()).isEqualTo(-30000L);
    }

    private String getUrl(String suffix) {
        return "http://localhost:" + port + suffix;
    }

    private String getAccountUrl() {
        return getAccountUrl("");
    }

    private String getAccountUrl(String suffix) {
        return getUrl("/account" + suffix);
    }

    private String getTransferUrl(String transactionId, String suffix) {
        return getUrl("/transfer/" + transactionId + suffix);
    }

    private String getTransferUrl(String transactionId) {
        return getTransferUrl(transactionId, "");
    }
}
