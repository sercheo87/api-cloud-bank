package com.cloud.bank.movements.model;

import com.cloud.bank.movements.enums.AccountStatus;
import com.cloud.bank.movements.enums.AccountType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "cb_tbl_account", schema = "public")
public class AccountEntity {
    @Id
    @Column("id_account")
    private Integer idAccount;

    @Column("state")
    @Builder.Default
    @Enumerated(EnumType.ORDINAL)
    private AccountStatus accountStatus = AccountStatus.ACTIVE;

    @Size(max = 75)
    @Column("account_number")
    private String accountNumber;

    @Column("account_type")
    private String accountType;

    @Column("initial_balance")
    private BigDecimal initialBalance;

    @Column("id_client")
    private Integer idClient;

    @Transient
    private AccountType accountTypeEnum;

}
