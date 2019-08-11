package com.maliarov.payaut.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Positive;

@ApiModel(description = "Input for transfer at a single account (debit/credit)")
public class TransferSingleAccount {
    @ApiModelProperty(notes = "Unique identifier of an account.", example = "0e9edd32-b067-405e-bf78-980c879bcd0c", required = true)
    private String accountId;
    @ApiModelProperty(notes = "Positive amount of cents to add/remove.", example = "500000", required = true)
    @Positive
    private Long amountInCents;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Long getAmountInCents() {
        return amountInCents;
    }

    public void setAmountInCents(Long amountInCents) {
        this.amountInCents = amountInCents;
    }
}
