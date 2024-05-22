package com.cloud.bank.movements.domainAccounts.dto;

import com.cloud.bank.movements.enums.AccountStatus;
import com.cloud.bank.movements.enums.AccountType;
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
public class CreateAccountRequest {
    @NotNull
    @NotEmpty
    String accountNumber;

    @NotNull
    AccountType accountType;

    @NotNull
    BigDecimal initialBalance;

    @NotNull
    AccountStatus accountStatus;

    @NotNull
    Integer idClient;

}
