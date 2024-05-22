package com.cloud.bank.clients.domainClients.services;

import com.cloud.bank.clients.domainClients.dto.ClientCreateRequest;
import com.cloud.bank.clients.domainClients.dto.ClientInformationResponse;
import com.cloud.bank.clients.domainClients.dto.ClientUpdateRequest;
import com.cloud.bank.clients.domainClients.exceptions.ClientAlreadyExistsByIdentification;
import com.cloud.bank.clients.domainClients.exceptions.ClientNotFoundException;
import com.cloud.bank.clients.domainClients.mappers.ClientInformationResponseMapper;
import com.cloud.bank.clients.domainClients.mappers.PersonMapper;
import com.cloud.bank.clients.enums.ClientStatus;
import com.cloud.bank.clients.model.ClientEntity;
import com.cloud.bank.clients.model.PersonEntity;
import com.cloud.bank.clients.repository.ClientRepository;
import com.cloud.bank.clients.repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@AllArgsConstructor
@Log4j2
public class ClientsManagementImpl implements ClientsManagement {

    ClientRepository clientRepository;
    PersonRepository personRepository;
    ClientInformationResponseMapper clientInformationResponseMapper;
    PersonMapper personMapper;

    @Override
    @Transactional
    public Mono<Void> createClient(ClientCreateRequest clientCreateRequest) {
        return Mono.just(clientCreateRequest)
            .publishOn(Schedulers.boundedElastic())
            .flatMap(this::validateNonExistsPersonByIdentification)
            .flatMap(this::savePersonAndCreateClient)
            .doOnSuccess(success -> log.info("Client created successfully"))
            .doOnError(error -> log.error("Error creating client"))
            .then();
    }

    @Override
    public Mono<Void> updateClient(Integer id, ClientUpdateRequest clientUpdateRequest) {
        return Mono.just(id)
            .flatMap(identifier -> clientRepository.findById(identifier))
            .switchIfEmpty(Mono.error(ClientNotFoundException.builder().identification(id).build()))
            .map(clientEntity -> {
                clientEntity.setPassword(clientUpdateRequest.getPassword());
                return clientEntity;
            })
            .flatMap(this::updateClient)
            .doOnSuccess(success -> log.info("Client updated successfully"))
            .doOnError(error -> log.error("Error updating client"))
            .then();
    }

    @Override
    public Mono<Void> deleteClient(Integer id) {
        return Mono.just(id)
            .flatMap(identifier -> clientRepository.findById(identifier))
            .switchIfEmpty(Mono.error(ClientNotFoundException.builder().identification(id).build()))
            .map(clientEntity -> {
                clientEntity.setState(ClientStatus.DELETED);
                return clientEntity;
            })
            .flatMap(this::updateClient)
            .doOnSuccess(success -> log.info("Client deleted successfully"))
            .doOnError(error -> log.error("Error deleting client"))
            .then();
    }

    @Override
    public Mono<ClientInformationResponse> getClient(Integer id) {
        return Mono.just(id)
            .flatMap(identifier -> clientRepository.findById(identifier))
            .switchIfEmpty(Mono.error(ClientNotFoundException.builder().identification(id).build()))
            .flatMap(clientEntity -> personRepository.findById(clientEntity.getIdPerson())
                .flatMap(personEntity -> Mono.just(clientInformationResponseMapper.toClientInformationResponse(personEntity))))
            .doOnSuccess(success -> log.info("Client found successfully"))
            .doOnError(error -> log.error("Error finding client"));
    }

    private ClientEntity createClientFromRequest(ClientCreateRequest clientCreateRequest, PersonEntity personEntitySaved) {
        return ClientEntity.builder()
            .idPerson(personEntitySaved.getIdPerson())
            .password(clientCreateRequest.getPassword())
            .state(ClientStatus.ACTIVE)
            .build();
    }

    private Mono<? extends ClientCreateRequest> validateNonExistsPersonByIdentification(ClientCreateRequest client) {
        return personRepository.existsByIdentification(client.getIdentification())
            .flatMap(exists -> exists ? Mono.error(
                ClientAlreadyExistsByIdentification
                    .builder()
                    .identification(client.getIdentification())
                    .build())
                : Mono.just(client)
            )
            .doOnSuccess(success -> log.info("Person not exists by identification"))
            .doOnError(error -> log.error("Person exists by identification"));
    }

    private Mono<Void> savePersonAndCreateClient(ClientCreateRequest clientCreateRequest) {
        return Mono.just(clientCreateRequest)
            .map(personMapper::toPersonEntity)
            .flatMap(personRepository::save)
            .map(savedPerson -> createClientFromRequest(clientCreateRequest, savedPerson))
            .flatMap(clientRepository::save)
            .doOnSuccess(success -> log.info("Entity person saved successfully"))
            .then();
    }

    private Mono<Void> updateClient(ClientEntity clientEntity) {
        return Mono.just(clientEntity)
            .flatMap(clientRepository::save)
            .doOnSuccess(success -> log.info("Client updated successfully"))
            .doOnError(error -> log.error("Error updating client"))
            .then();
    }

}
