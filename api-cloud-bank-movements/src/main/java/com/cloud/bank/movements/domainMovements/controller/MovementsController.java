package com.cloud.bank.movements.domainMovements.controller;

import com.cloud.bank.movements.domainMovements.dto.CreateMovementRequest;
import com.cloud.bank.movements.domainMovements.dto.ReportMovementRequest;
import com.cloud.bank.movements.dto.BaseResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public interface MovementsController {

    @ApiResponse(responseCode = "200", description = "Movement registered successfully")
    @PostMapping(path = "/movimientos", consumes = "application/json", produces = "application/json")
    Mono<ResponseEntity<BaseResponse>> registerMovement(@Valid @RequestBody CreateMovementRequest createMovementRequest);

    @ApiResponse(responseCode = "200", description = "Movement query successfully")
    @PostMapping(path = "/movimientos/reporte", consumes = "application/json", produces = "application/json")
    Mono<ResponseEntity<BaseResponse>> reportMovements(@Valid @RequestBody ReportMovementRequest reportMovementRequest);

}
