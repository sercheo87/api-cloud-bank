package com.cloud.bank.movements.repository;

import com.cloud.bank.movements.model.MovementEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Repository
public interface MovementRepository extends ReactiveCrudRepository<MovementEntity, Integer> {

    @Transactional(readOnly = true)
    Mono<MovementEntity> findFirstByOrderByIdMovementDesc();
}
