package com.maliarov.payaut.web.controller;

import com.maliarov.payaut.web.dto.AccountBalanceDto;
import com.maliarov.payaut.web.dto.TransferBetweenAccountsDto;
import com.maliarov.payaut.web.dto.TransferBetweenAccountsResultDto;
import com.maliarov.payaut.web.dto.TransferSingleAccountDto;
import com.maliarov.payaut.web.service.TransferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("transfer")
@Api(value = "Transfer")
public class TransferController {
    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @RequestMapping(value = "/{transactionId}/debit", method = RequestMethod.PATCH)
    @ApiOperation("Debit on a specified account")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Transferred successfully"),
            @ApiResponse(code = 400, message = "Validation failed"), @ApiResponse(code = 404, message = "Account not found")})
    AccountBalanceDto debit(@PathVariable("transactionId") String transactionId, @Valid @RequestBody TransferSingleAccountDto input) {
        return transferService.debit(transactionId, input);
    }

    @RequestMapping(value = "/{transactionId}/credit", method = RequestMethod.PATCH)
    @ApiOperation("Credit on a specified account")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Transferred successfully"),
            @ApiResponse(code = 400, message = "Validation failed"), @ApiResponse(code = 404, message = "Account not found")})
    AccountBalanceDto credit(@PathVariable("transactionId") String transactionId, @Valid @RequestBody TransferSingleAccountDto input) {
        return transferService.credit(transactionId, input);
    }

    @RequestMapping(value = "/{transactionId}", method = RequestMethod.PATCH)
    @ApiOperation("Transfer between two accounts")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Transferred successfully"),
            @ApiResponse(code = 400, message = "Validation failed"), @ApiResponse(code = 404, message = "Account not found")})
    TransferBetweenAccountsResultDto transferBetweenAccounts(@PathVariable("transactionId") String transactionId,
                                                             @Valid @RequestBody TransferBetweenAccountsDto input) {
        return transferService.transferBetweenAccounts(transactionId, input);
    }
}
