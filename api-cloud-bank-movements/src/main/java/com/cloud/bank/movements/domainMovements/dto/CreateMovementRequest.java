package com.cloud.bank.movements.domainMovements.dto;

import com.cloud.bank.movements.enums.MovementType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateMovementRequest {

    @NotNull
    @NotEmpty
    String accountNumber;

    @NotNull
    MovementType typeMovement;

    @NotNull
    BigDecimal amount;
}
