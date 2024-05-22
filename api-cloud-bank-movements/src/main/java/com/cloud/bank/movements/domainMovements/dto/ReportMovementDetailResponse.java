package com.cloud.bank.movements.domainMovements.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReportMovementDetailResponse {
    String dateMovement;
    String name;
    String accountNumber;
    String accountType;
    Double initialBalance;
    String state;
    String typeMovement;
    String amount;
    BigDecimal balance;
}
