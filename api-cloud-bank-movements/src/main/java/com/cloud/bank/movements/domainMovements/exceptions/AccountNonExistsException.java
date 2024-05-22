package com.cloud.bank.movements.domainMovements.exceptions;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AccountNonExistsException extends RuntimeException {

    String accountNumber;

    @Override
    public String getMessage() {
        return String.format("Account with account number [%s] does not exist", accountNumber);
    }
    
}
