package com.cloud.bank.movements.model;

import com.cloud.bank.movements.enums.MovementType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "cb_tbl_movements", schema = "public")
public class MovementEntity {
    @Id
    @Column("id_movement")
    private Integer idMovement;

    @Column("date_movement")
    private LocalDateTime dateMovement;

    @Column("amount")
    private BigDecimal amount;

    @Column("id_account")
    private Integer idAccount;

    @Column("type_movement")
    private String typeMovement;

    @Column("balance")
    private BigDecimal balance;

    @Transient
    private MovementType typeMovementEnum;

}
