package com.cloud.bank.movements.repository;

import com.cloud.bank.movements.model.AccountEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveCrudRepository<AccountEntity, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM AccountEntity a WHERE a.accountNumber = :accountNumber")
    Mono<Boolean> existsAccountByAccountNumber(String accountNumber);

    @Transactional(readOnly = true)
    Mono<AccountEntity> findByAccountNumber(String accountNumber);

}
