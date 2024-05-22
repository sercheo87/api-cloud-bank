package com.cloud.bank.movements.domainAccounts.services;

import com.cloud.bank.movements.domainAccounts.dto.CreateAccountRequest;
import com.cloud.bank.movements.domainAccounts.mappers.AccountMapper;
import com.cloud.bank.movements.domainMovements.exceptions.AccountNonExistsException;
import com.cloud.bank.movements.repository.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@AllArgsConstructor
@Log4j2
public class AccountManagementImpl implements AccountManagement {

    AccountRepository accountRepository;
    AccountMapper accountMapper;

    @Override
    public Mono<Void> createAccount(CreateAccountRequest createAccountRequest) {
        return Mono.just(createAccountRequest)
            .publishOn(Schedulers.boundedElastic())
            .flatMap(this::validateNonExistsAccount)
            .flatMap(this::saveAccount)
            .doOnSuccess(success -> log.info("Account created successfully"))
            .doOnError(error -> log.error("Error creating account"))
            .then();

    }

    private Mono<? extends CreateAccountRequest> validateNonExistsAccount(CreateAccountRequest createAccountRequest) {
        return accountRepository.existsAccountByAccountNumber(createAccountRequest.getAccountNumber())
            .flatMap(exists -> exists ? Mono.error(AccountNonExistsException
                .builder()
                .accountNumber(createAccountRequest.getAccountNumber())
                .build())
                : Mono.just(createAccountRequest))
            .doOnSuccess(success -> log.info("Account number {} does not exist", createAccountRequest.getAccountNumber()))
            .doOnError(error -> log.error("Account number {} already exists", createAccountRequest.getAccountNumber()));
    }

    private Mono<Void> saveAccount(CreateAccountRequest createAccountRequest) {
        return Mono.just(createAccountRequest)
            .map(accountMapper::toAccountEntity)
            .map(accountRepository::save)
            .flatMap(accountEntityMono -> accountEntityMono.flatMap(accountEntity -> {
                log.info("Account identifier: {}", accountEntity.getIdAccount());
                return Mono.just(accountEntity);
            }))
            .doOnSuccess(success -> log.info("Account entity created successfully"))
            .doOnError(error -> log.error("Error creating entity account"))
            .then();
    }

}
