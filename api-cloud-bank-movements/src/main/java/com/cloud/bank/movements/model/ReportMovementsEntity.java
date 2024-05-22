package com.cloud.bank.movements.model;

import lombok.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "cb_vw_report_movements", schema = "public")
public class ReportMovementsEntity {

    @Column("id_movement")
    private Integer idMovement;

    @Column("date_movement")
    private LocalDateTime dateMovement;

    @Column("id_client")
    private Integer idClient;

    @Column("name")
    private String name;

    @Column("account_number")
    private String accountNumber;

    @Column("account_type")
    private String accountType;

    @Column("initial_balance")
    private Double initialBalance;

    @Column("type_movement")
    private String typeMovement;

    @Column("amount")
    private BigDecimal amount;

    @Column("balance")
    private BigDecimal balance;

    @Column("state")
    private String state;
    
}
