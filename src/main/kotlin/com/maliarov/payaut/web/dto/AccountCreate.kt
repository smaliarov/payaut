package com.maliarov.payaut.web.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

import javax.validation.constraints.Size

@ApiModel(description = "Input for creating a new account")
class AccountCreate {
    @Size(min = 3, max = 255)
    @ApiModelProperty(notes = "Owner. Has length of 3 to 255 characters.", example = "Sergii Maliarov", required = true)
    var owner: String? = null
}
