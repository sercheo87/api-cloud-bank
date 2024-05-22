package com.cloud.bank.movements.domainAccounts.controller;

import com.cloud.bank.movements.domainAccounts.dto.CreateAccountRequest;
import com.cloud.bank.movements.dto.BaseResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public interface AccountController {

    @ApiResponse(responseCode = "200", description = "Account created successfully")
    @PutMapping(path = "/cuentas", consumes = "application/json", produces = "application/json")
    Mono<ResponseEntity<BaseResponse>> createAccount(@Valid @RequestBody CreateAccountRequest createAccountRequest);

}
