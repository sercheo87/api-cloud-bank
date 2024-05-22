package com.cloud.bank.clients.repository;

import com.cloud.bank.clients.model.PersonEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Repository
public interface PersonRepository extends ReactiveCrudRepository<PersonEntity, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM PersonEntity p WHERE p.identification = :identification")
    Mono<Boolean> existsByIdentification(String identification);

}