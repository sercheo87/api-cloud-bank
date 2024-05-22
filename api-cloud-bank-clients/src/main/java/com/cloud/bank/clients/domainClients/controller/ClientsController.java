package com.cloud.bank.clients.domainClients.controller;

import com.cloud.bank.clients.domainClients.dto.ClientCreateRequest;
import com.cloud.bank.clients.domainClients.dto.ClientInformationResponse;
import com.cloud.bank.clients.domainClients.dto.ClientUpdateRequest;
import com.cloud.bank.clients.dto.BaseResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public interface ClientsController {

    @ApiResponse(responseCode = "200", description = "Client created successfully")
    @PutMapping(path = "/clientes", consumes = "application/json", produces = "application/json")
    Mono<ResponseEntity<BaseResponse>> createClient(@Valid @RequestBody ClientCreateRequest clientCreateRequest);

    @ApiResponse(responseCode = "200", description = "Client updated successfully")
    @PostMapping(path = "/clientes/{id}", consumes = "application/json", produces = "application/json")
    Mono<ResponseEntity<BaseResponse>> updateClient(@PathVariable Integer id, @Valid @RequestBody ClientUpdateRequest clientUpdateRequest);

    @ApiResponse(responseCode = "200", description = "Client deleted successfully")
    @DeleteMapping(path = "/clientes/{id}", produces = "application/json")
    Mono<ResponseEntity<BaseResponse>> deleteClient(@PathVariable Integer id);

    @ApiResponse(responseCode = "200", description = "Client retrieved successfully")
    @GetMapping(path = "/clientes/{id}", produces = "application/json")
    Mono<ResponseEntity<ClientInformationResponse>> getClient(@PathVariable Integer id);

}
