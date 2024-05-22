package com.cloud.bank.clients.domainClients.exceptions;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ClientNotFoundException extends RuntimeException {

    Integer identification;

    @Override
    public String getMessage() {
        return String.format("Client with identification [%s] not found", identification);
    }

}
