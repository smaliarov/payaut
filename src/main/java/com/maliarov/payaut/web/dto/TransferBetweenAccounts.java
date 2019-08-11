package com.maliarov.payaut.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@ApiModel(description = "Input for transfer between two accounts")
public class TransferBetweenAccounts {
    @ApiModelProperty(notes = "Unique identifier of account from which transfer the money.", example = "0e9edd32-b067-405e-bf78-980c879bcd0c", required = true)
    @NotBlank
    private String accountIdFrom;
    @ApiModelProperty(notes = "Unique identifier of account to which transfer the money.", example = "0e9edd32-b067-405e-bf78-980c879bcd0c", required = true)
    @NotBlank
    private String accountIdTo;
    @ApiModelProperty(notes = "Positive amount of cents to transfer.", example = "500000", required = true)
    @Positive
    private Long amountInCents;

    public String getAccountIdFrom() {
        return accountIdFrom;
    }

    public void setAccountIdFrom(String accountIdFrom) {
        this.accountIdFrom = accountIdFrom;
    }

    public String getAccountIdTo() {
        return accountIdTo;
    }

    public void setAccountIdTo(String accountIdTo) {
        this.accountIdTo = accountIdTo;
    }

    public Long getAmountInCents() {
        return amountInCents;
    }

    public void setAmountInCents(Long amountInCents) {
        this.amountInCents = amountInCents;
    }
}
