package com.maliarov.payaut.web.controller;

import com.maliarov.payaut.web.dto.AccountBalance;
import com.maliarov.payaut.web.dto.TransferBetweenAccounts;
import com.maliarov.payaut.web.dto.TransferBetweenAccountsResult;
import com.maliarov.payaut.web.dto.TransferSingleAccount;
import com.maliarov.payaut.web.service.TransferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
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
    @ApiResponses(value = {@ApiResponse(code = 202, message = "Transferred successfully"),
            @ApiResponse(code = 400, message = "Validation failed"), @ApiResponse(code = 404, message = "Account not found")})
    @ResponseStatus(HttpStatus.ACCEPTED)
    AccountBalance debit(@PathVariable("transactionId") String transactionId, @Valid @RequestBody TransferSingleAccount input) {
        return transferService.debit(transactionId, input);
    }

    @RequestMapping(value = "/{transactionId}/credit", method = RequestMethod.PATCH)
    @ApiOperation("Credit on a specified account")
    @ApiResponses(value = {@ApiResponse(code = 202, message = "Transferred successfully"),
            @ApiResponse(code = 400, message = "Validation failed"), @ApiResponse(code = 404, message = "Account not found")})
    @ResponseStatus(HttpStatus.ACCEPTED)
    AccountBalance credit(@PathVariable("transactionId") String transactionId, @Valid @RequestBody TransferSingleAccount input) {
        return transferService.credit(transactionId, input);
    }

    @RequestMapping(value = "/{transactionId}", method = RequestMethod.PATCH)
    @ApiOperation("Transfer between two accounts")
    @ApiResponses(value = {@ApiResponse(code = 202, message = "Transferred successfully"),
            @ApiResponse(code = 400, message = "Validation failed"), @ApiResponse(code = 404, message = "Account not found")})
    @ResponseStatus(HttpStatus.ACCEPTED)
    TransferBetweenAccountsResult transferBetweenAccounts(@PathVariable("transactionId") String transactionId,
                                                          @Valid @RequestBody TransferBetweenAccounts input) {
        return transferService.transferBetweenAccounts(transactionId, input);
    }
}
