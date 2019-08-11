package com.maliarov.payaut.web.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

@ApiModel(description = "Input for transfer between two accounts")
class TransferBetweenAccounts {
    @ApiModelProperty(notes = "Unique identifier of account from which transfer the money.", example = "0e9edd32-b067-405e-bf78-980c879bcd0c", required = true)
    @NotBlank
    var accountIdFrom: String? = null
    @ApiModelProperty(notes = "Unique identifier of account to which transfer the money.", example = "0e9edd32-b067-405e-bf78-980c879bcd0c", required = true)
    @NotBlank
    var accountIdTo: String? = null
    @ApiModelProperty(notes = "Positive amount of cents to transfer.", example = "500000", required = true)
    @Positive
    var amountInCents: Long? = null
}
