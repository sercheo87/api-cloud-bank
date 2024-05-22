package com.cloud.bank.clients.domainClients.exceptions;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ClientAlreadyExistsByIdentification extends RuntimeException {

    String identification;

    @Override
    public String getMessage() {
        return String.format("Client with identification [%s] already exists", identification);
    }

}

