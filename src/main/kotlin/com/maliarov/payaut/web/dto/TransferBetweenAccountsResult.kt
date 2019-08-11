package com.maliarov.payaut.web.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(description = "Result of the transfer between two accounts")
class TransferBetweenAccountsResult {
    @ApiModelProperty(notes = "Balance of the account from which money were transferred", required = true)
    var accountBalanceFrom: AccountBalance? = null
    @ApiModelProperty(notes = "Balance of the account to which money were transferred", required = true)
    var accountBalanceTo: AccountBalance? = null

    constructor() {}

    constructor(accountBalanceFrom: AccountBalance, accountBalanceTo: AccountBalance) {
        this.accountBalanceFrom = accountBalanceFrom
        this.accountBalanceTo = accountBalanceTo
    }
}
