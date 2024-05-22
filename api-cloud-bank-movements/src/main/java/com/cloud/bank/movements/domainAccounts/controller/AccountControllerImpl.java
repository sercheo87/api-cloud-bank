package com.cloud.bank.movements.domainAccounts.controller;

import com.cloud.bank.movements.domainAccounts.dto.CreateAccountRequest;
import com.cloud.bank.movements.domainAccounts.services.AccountManagement;
import com.cloud.bank.movements.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Log4j2
@AllArgsConstructor
@Component
public class AccountControllerImpl implements AccountController {

    AccountManagement accountManagement;

    @Override
    public Mono<ResponseEntity<BaseResponse>> createAccount(CreateAccountRequest createAccountRequest) {
        log.debug("Creating account {}", createAccountRequest);
        return accountManagement.createAccount(createAccountRequest)
            .then(Mono.just(ResponseEntity.created(null)
                .body(BaseResponse.builder().message("Account created successfully").code("000").build())));
    }

}
