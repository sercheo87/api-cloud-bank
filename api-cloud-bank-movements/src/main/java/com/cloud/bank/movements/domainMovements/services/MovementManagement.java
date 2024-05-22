package com.cloud.bank.movements.domainMovements.services;

import com.cloud.bank.movements.domainMovements.dto.CreateMovementRequest;
import com.cloud.bank.movements.domainMovements.dto.ReportMovementDetailResponse;
import com.cloud.bank.movements.domainMovements.dto.ReportMovementRequest;
import reactor.core.publisher.Mono;

import java.util.List;

public interface MovementManagement {

    Mono<Void> registerMovement(CreateMovementRequest createMovementRequest);

    Mono<List<ReportMovementDetailResponse>> reportMovements(ReportMovementRequest reportMovementRequest);

}
