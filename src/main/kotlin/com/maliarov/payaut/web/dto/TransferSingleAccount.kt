package com.maliarov.payaut.web.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

import javax.validation.constraints.Positive

@ApiModel(description = "Input for transfer at a single account (debit/credit)")
class TransferSingleAccount {
    @ApiModelProperty(notes = "Unique identifier of an account.", example = "0e9edd32-b067-405e-bf78-980c879bcd0c", required = true)
    var accountId: String? = null
    @ApiModelProperty(notes = "Positive amount of cents to add/remove.", example = "500000", required = true)
    @Positive
    var amountInCents: Long? = null
}
