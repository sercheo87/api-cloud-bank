package com.cloud.bank.movements.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum AccountType {
    SAVING("Ahorros"),
    CURRENT("Corriente");

    String accountType;

    @JsonCreator
    public static AccountType fromString(String accountType) {
        return Arrays.stream(AccountType.values())
            .filter(type -> type.getAccountType().equalsIgnoreCase(accountType))
            .findFirst()
            .orElse(null);
    }

    public static AccountType fromOrdinal(int ordinal) {
        for (AccountType type : AccountType.values()) {
            if (type.ordinal() == ordinal) {
                return type;
            }
        }
        return null;
    }

    @JsonValue
    public String toValue() {
        return getAccountType();
    }

}
