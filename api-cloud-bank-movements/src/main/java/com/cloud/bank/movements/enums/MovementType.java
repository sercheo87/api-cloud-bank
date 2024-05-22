package com.cloud.bank.movements.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum MovementType {
    DEPOSIT("Deposito"),
    WITHDRAWAL("Retiro");

    String movementType;

    @JsonCreator
    public static MovementType fromString(String movementType) {
        for (MovementType type : MovementType.values()) {
            if (type.getMovementType().equalsIgnoreCase(movementType)) {
                return type;
            }
        }
        return null;
    }

    public static MovementType fromOrdinal(int ordinal) {
        for (MovementType type : MovementType.values()) {
            if (type.ordinal() == ordinal) {
                return type;
            }
        }
        return null;
    }

    @JsonValue
    public String toValue() {
        return getMovementType();
    }

}
