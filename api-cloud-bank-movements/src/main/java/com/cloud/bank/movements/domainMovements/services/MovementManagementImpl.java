package com.cloud.bank.movements.domainMovements.services;

import com.cloud.bank.movements.domainMovements.dto.*;
import com.cloud.bank.movements.domainMovements.exceptions.AccountNonExistsException;
import com.cloud.bank.movements.domainMovements.exceptions.OverdrawnAccountException;
import com.cloud.bank.movements.domainMovements.mappers.MovementMapper;
import com.cloud.bank.movements.domainMovements.mappers.ReportMovementMapper;
import com.cloud.bank.movements.enums.MovementType;
import com.cloud.bank.movements.model.MovementEntity;
import com.cloud.bank.movements.repository.AccountRepository;
import com.cloud.bank.movements.repository.MovementRepository;
import com.cloud.bank.movements.repository.ReportMovementRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Log4j2
public class MovementManagementImpl implements MovementManagement {

    AccountRepository accountRepository;
    MovementRepository movementRepository;
    ReportMovementRepository reportMovementRepository;
    MovementMapper movementMapper;
    ReportMovementMapper reportMovementMapper;

    @Override
    public Mono<Void> registerMovement(CreateMovementRequest createMovementRequest) {
        return Mono.just(createMovementRequest)
            .flatMap(this::validateAccountExists)
            .flatMap(this::saveMovement)
            .doOnSuccess(success -> log.info("Movement registered successfully"))
            .doOnError(error -> log.error("Error registering movement"))
            .then();
    }

    @Override
    public Mono<List<ReportMovementDetailResponse>> reportMovements(ReportMovementRequest reportMovementRequest) {
        log.debug("Reporting movements {}", reportMovementRequest);
        return Mono.just(reportMovementRequest)
            .flatMap(this::getReportMovements)
            .doOnSuccess(success -> log.info("Report movements generated successfully"))
            .doOnError(error -> log.error("Error generating report movements"));
    }

    private Mono<List<ReportMovementDetailResponse>> getReportMovements(ReportMovementRequest reportMovementRequest) {
        return Mono.just(ReportMovementResponse.builder().build())
            .flatMap(reportMovementResponse -> reportMovementRepository.findAllByIdClientAndDateMovementGreaterThanEqualAndDateMovementIsLessThanEqual(reportMovementRequest.getIdClient(), reportMovementRequest.getDateStart(), reportMovementRequest.getDateEnd())
                .map(reportMovementMapper::toReportMovementDetailResponse)
                .collectList())
            .doOnSuccess(success -> log.info("Report movements generated successfully"))
            .doOnError(error -> log.error("Error generating report movements"));

    }

    private Mono<Void> saveMovement(CreateMovementRequest createMovementRequest) {
        return Mono.just(DetailMovementRegister.builder()
                .createMovementRequest(createMovementRequest)
                .movementEntity(movementMapper.toMovementEntity(createMovementRequest))
                .build())
            .flatMap(this::getAccount)
            .flatMap(this::getLastMovement)
            .flatMap(this::calculateCurrentBalance)
            .flatMap(this::validateOverdraft)
            .flatMap(this::calculateAvailableBalance)
            .flatMap(this::fillGeneralData)
            .flatMap(movementRepository::save)
            .subscribeOn(Schedulers.immediate())
            .doOnSuccess(movementEntity -> log.info("Movement entity created successfully with identifier: {}", movementEntity.getIdMovement()))
            .doOnError(error -> log.error("Error creating entity movement"))
            .then();
    }

    private Mono<? extends CreateMovementRequest> validateAccountExists(CreateMovementRequest createMovementRequest) {
        return accountRepository.existsAccountByAccountNumber(createMovementRequest.getAccountNumber())
            .flatMap(exists -> exists ? Mono.just(createMovementRequest)
                : Mono.error(AccountNonExistsException.builder().accountNumber(createMovementRequest.getAccountNumber()).build()))
            .doOnSuccess(success -> log.info("Account number {} exists", createMovementRequest.getAccountNumber()))
            .doOnError(error -> log.error("Account number {} does not exist", createMovementRequest.getAccountNumber()));
    }

    private Mono<DetailMovementRegister> getAccount(DetailMovementRegister detailMovementRegister) {
        return Mono.fromCallable(() -> accountRepository.findByAccountNumber(detailMovementRegister.getCreateMovementRequest().getAccountNumber()))
            .subscribeOn(Schedulers.immediate())
            .flatMap(accountEntity -> accountEntity.flatMap(accountEntity1 -> {
                log.info("Account identifier: {}", accountEntity1.getIdAccount());
                detailMovementRegister.setAccountEntity(accountEntity1);
                return Mono.just(detailMovementRegister);
            }))
            .subscribeOn(Schedulers.immediate())
            .log();
    }

    private Mono<DetailMovementRegister> getLastMovement(DetailMovementRegister detailMovementRegister) {
        return Mono.fromCallable(() -> movementRepository.findFirstByOrderByIdMovementDesc())
            .subscribeOn(Schedulers.immediate())
            .flatMap(movementEntity -> movementEntity.flatMap(movementEntity1 -> {
                log.info("Movement identifier: {}", movementEntity1.getIdMovement());
                detailMovementRegister.setMovementEntityLast(Optional.of(movementEntity1));
                return Mono.just(detailMovementRegister);
            }))
            .switchIfEmpty(Mono.just(detailMovementRegister))
            .subscribeOn(Schedulers.immediate())
            .log();
    }

    private Mono<DetailMovementRegister> calculateAvailableBalance(DetailMovementRegister detailMovementRegister) {
        log.debug("Calculating available balance");
        if (MovementType.DEPOSIT == detailMovementRegister.getMovementEntity().getTypeMovementEnum()) {
            detailMovementRegister.getMovementEntity().setBalance(detailMovementRegister.getMovementEntity().getBalance().add(detailMovementRegister.getMovementEntity().getAmount()));
        } else {
            detailMovementRegister.getMovementEntity().setBalance(detailMovementRegister.getMovementEntity().getBalance().subtract(detailMovementRegister.getMovementEntity().getAmount()));
        }
        return Mono.just(detailMovementRegister);
    }

    private Mono<MovementEntity> fillGeneralData(DetailMovementRegister detailMovementRegister) {
        log.debug("Filling general data");
        detailMovementRegister.getMovementEntity().setDateMovement(LocalDateTime.now());
        detailMovementRegister.getMovementEntity().setIdAccount(detailMovementRegister.getAccountEntity().getIdAccount());
        return Mono.just(detailMovementRegister.getMovementEntity());
    }

    private Mono<DetailMovementRegister> validateOverdraft(DetailMovementRegister detailMovementRegister) {
        log.debug("Validating overdraft");
        if (MovementType.WITHDRAWAL == detailMovementRegister.getMovementEntity().getTypeMovementEnum()) {
            if (detailMovementRegister.getMovementEntity().getBalance().compareTo(detailMovementRegister.getMovementEntity().getAmount()) < 0) {
                return Mono.error(OverdrawnAccountException.builder().amount(detailMovementRegister.getMovementEntity().getAmount().toString()).build());
            }
        }
        return Mono.just(detailMovementRegister);
    }

    private Mono<DetailMovementRegister> calculateCurrentBalance(DetailMovementRegister detailMovementRegister) {
        log.debug("Calculating current balance");
        BigDecimal balance;

        if (detailMovementRegister.getMovementEntityLast().isPresent()) {
            balance = detailMovementRegister.getMovementEntityLast().get().getBalance();
        } else {
            balance = detailMovementRegister.getAccountEntity().getInitialBalance();
        }

        detailMovementRegister.getMovementEntity().setBalance(balance);
        return Mono.just(detailMovementRegister);
    }

}
