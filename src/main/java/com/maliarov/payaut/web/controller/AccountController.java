package com.maliarov.payaut.web.controller;

import com.maliarov.payaut.repository.AccountRepository;
import com.maliarov.payaut.web.dto.AccountBalanceDto;
import com.maliarov.payaut.web.dto.AccountCreateDto;
import com.maliarov.payaut.web.dto.AccountDto;
import com.maliarov.payaut.web.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("account")
@Api(value = "Account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation("Creates an account")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Created successfully"),
            @ApiResponse(code = 400, message = "Validation failed")})
    AccountDto create(@Valid @RequestBody AccountCreateDto input) {
        return accountService.create(input);
    }

    @RequestMapping(value = "/{accountId}/balance", method = RequestMethod.GET)
    @ApiOperation("Gets balance of an account")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    AccountBalanceDto balance(@PathVariable("accountId") String accountId) {
        return accountService.getBalance(accountId);
    }
}
