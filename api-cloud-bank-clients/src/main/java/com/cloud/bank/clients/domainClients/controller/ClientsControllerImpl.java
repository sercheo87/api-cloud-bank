package com.cloud.bank.clients.domainClients.controller;

import com.cloud.bank.clients.domainClients.dto.ClientCreateRequest;
import com.cloud.bank.clients.domainClients.dto.ClientInformationResponse;
import com.cloud.bank.clients.domainClients.dto.ClientUpdateRequest;
import com.cloud.bank.clients.domainClients.services.ClientsManagement;
import com.cloud.bank.clients.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Log4j2
@AllArgsConstructor
@Component
public class ClientsControllerImpl implements ClientsController {

    ClientsManagement clientsManagement;

    @Override
    public Mono<ResponseEntity<BaseResponse>> createClient(ClientCreateRequest clientCreateRequest) {
        log.debug("Creating client {}", clientCreateRequest);
        return clientsManagement.createClient(clientCreateRequest)
            .then(Mono.just(ResponseEntity.created(null)
                .body(BaseResponse.builder().message("Client created successfully").code("000").build())));
    }

    @Override
    public Mono<ResponseEntity<BaseResponse>> updateClient(Integer id, ClientUpdateRequest clientUpdateRequest) {
        return clientsManagement.updateClient(id, clientUpdateRequest)
            .then(Mono.just(ResponseEntity.ok(BaseResponse.builder().message("Client updated successfully").code("000").build())));
    }

    @Override
    public Mono<ResponseEntity<BaseResponse>> deleteClient(Integer id) {
        return clientsManagement.deleteClient(id)
            .then(Mono.just(ResponseEntity.ok(BaseResponse.builder().message("Client deleted successfully").code("000").build())));
    }

    @Override
    public Mono<ResponseEntity<ClientInformationResponse>> getClient(Integer id) {
        return clientsManagement.getClient(id)
            .map(ResponseEntity::ok);
    }

}
