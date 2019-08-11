package com.maliarov.payaut.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Size;

@ApiModel(description = "Input for creating a new account")
public class AccountCreate {
    @Size(min = 3, max = 255)
    @ApiModelProperty(notes = "Owner. Has length of 3 to 255 characters.", example = "Sergii Maliarov", required = true)
    private String owner;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
