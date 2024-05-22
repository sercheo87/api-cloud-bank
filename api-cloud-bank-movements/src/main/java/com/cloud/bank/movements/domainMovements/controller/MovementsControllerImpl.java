package com.cloud.bank.movements.domainMovements.controller;

import com.cloud.bank.movements.domainMovements.dto.CreateMovementRequest;
import com.cloud.bank.movements.domainMovements.dto.ReportMovementRequest;
import com.cloud.bank.movements.domainMovements.dto.ReportMovementResponse;
import com.cloud.bank.movements.domainMovements.services.MovementManagement;
import com.cloud.bank.movements.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Log4j2
@AllArgsConstructor
@Component
public class MovementsControllerImpl implements MovementsController {

    MovementManagement movementManagement;

    @Override
    public Mono<ResponseEntity<BaseResponse>> registerMovement(CreateMovementRequest createMovementRequest) {
        log.debug("Registering movement {}", createMovementRequest);
        return movementManagement.registerMovement(createMovementRequest)
            .then(Mono.just(ResponseEntity.created(null)
                .body(BaseResponse.builder().message("Movement created successfully").code("000").build())));
    }

    @Override
    public Mono<ResponseEntity<BaseResponse>> reportMovements(ReportMovementRequest reportMovementRequest) {
        log.debug("Reporting movements {}", reportMovementRequest);
        return movementManagement.reportMovements(reportMovementRequest)
            .map(reportMovementResponse -> ReportMovementResponse.builder()
                .movements(reportMovementResponse)
                .code("000")
                .message("Movement query successfully")
                .build())
            .flatMap(reportMovementResponse -> Mono.just(ResponseEntity.ok(reportMovementResponse)));
    }

}
