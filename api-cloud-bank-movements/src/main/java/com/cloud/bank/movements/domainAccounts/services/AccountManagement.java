package com.cloud.bank.movements.domainAccounts.services;

import com.cloud.bank.movements.domainAccounts.dto.CreateAccountRequest;
import reactor.core.publisher.Mono;

public interface AccountManagement {

    Mono<Void> createAccount(CreateAccountRequest createAccountRequest);

}
