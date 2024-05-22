package com.cloud.bank.clients.model;

import com.cloud.bank.clients.enums.ClientStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "cb_tbl_client", schema = "public")
public class ClientEntity {

    @Id
    @Size(max = 100)
    @Column("id_client")
    private Integer idClient;

    @Size(max = 100)
    @Column("password")
    private String password;

    @Size(max = 10)
    @NotNull
    @Column("state")
    @Builder.Default
    @Enumerated(EnumType.ORDINAL)
    private ClientStatus state = ClientStatus.ACTIVE;

    @Column("id_person")
    private Integer idPerson;

    @Column("last_update")
    private LocalDateTime lastUpdate;

    @Column("created")
    private LocalDateTime created;

}