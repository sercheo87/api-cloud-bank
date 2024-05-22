package com.cloud.bank.clients.domainClients.services;

import com.cloud.bank.clients.domainClients.dto.ClientCreateRequest;
import com.cloud.bank.clients.domainClients.dto.ClientInformationResponse;
import com.cloud.bank.clients.domainClients.dto.ClientUpdateRequest;
import reactor.core.publisher.Mono;

public interface ClientsManagement {

    Mono<Void> createClient(ClientCreateRequest clientCreateRequest);

    Mono<Void> updateClient(Integer id, ClientUpdateRequest clientUpdateRequest);

    Mono<Void> deleteClient(Integer id);

    Mono<ClientInformationResponse> getClient(Integer id);

}
