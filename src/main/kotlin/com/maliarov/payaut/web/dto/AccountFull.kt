package com.maliarov.payaut.web.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(description = "Full information about account")
class AccountFull {
    @ApiModelProperty(notes = "Unique identifier of an account.", example = "0e9edd32-b067-405e-bf78-980c879bcd0c", required = true)
    var id: String? = null
    @ApiModelProperty(notes = "Owner. Has length of 3 to 255 characters.", example = "Sergii Maliarov", required = true)
    var owner: String? = null
    @ApiModelProperty(notes = "Amount of cents at the account.", example = "500000", required = true)
    var amountInCents: Long? = null
}
