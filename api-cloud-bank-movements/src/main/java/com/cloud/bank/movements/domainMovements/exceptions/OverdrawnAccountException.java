package com.cloud.bank.movements.domainMovements.exceptions;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OverdrawnAccountException extends RuntimeException {
    String amount;

    @Override
    public String getMessage() {
        return String.format("Account overdrawn by %s", amount);
    }
}
