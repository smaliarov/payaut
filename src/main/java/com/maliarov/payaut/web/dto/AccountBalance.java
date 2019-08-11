package com.maliarov.payaut.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Information about account balance")
public class AccountBalance {
    @ApiModelProperty(notes = "Unique identifier of an account.", example = "0e9edd32-b067-405e-bf78-980c879bcd0c", required = true)
    private String id;
    @ApiModelProperty(notes = "Amount of cents at the account.", example = "500000", required = true)
    private Long amountInCents;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getAmountInCents() {
        return amountInCents;
    }

    public void setAmountInCents(Long amountInCents) {
        this.amountInCents = amountInCents;
    }
}
