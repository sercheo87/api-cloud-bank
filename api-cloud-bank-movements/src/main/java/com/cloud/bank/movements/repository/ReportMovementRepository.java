package com.cloud.bank.movements.repository;

import com.cloud.bank.movements.model.ReportMovementsEntity;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.util.Date;

@Repository
public interface ReportMovementRepository extends ReactiveSortingRepository<ReportMovementsEntity, Integer> {

    @Transactional(readOnly = true)
    Flux<ReportMovementsEntity> findAllByIdClientAndDateMovementGreaterThanEqualAndDateMovementIsLessThanEqual(Integer idClient, Date dateStart, Date dateEnd);

}
