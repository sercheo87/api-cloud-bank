package com.cloud.bank.movements.domainAccounts.exceptions;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AccountAlreadyExistsException extends RuntimeException {

    String accountNumber;

    @Override
    public String getMessage() {
        return String.format("Account with account number [%s] already exists", accountNumber);
    }

}
