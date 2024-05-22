package com.cloud.bank.movements.domainMovements.dto;

import com.cloud.bank.movements.model.AccountEntity;
import com.cloud.bank.movements.model.MovementEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DetailMovementRegister {
    CreateMovementRequest createMovementRequest;
    AccountEntity accountEntity;
    MovementEntity movementEntity;
    @Builder.Default
    Optional<MovementEntity> movementEntityLast = Optional.empty();
}
