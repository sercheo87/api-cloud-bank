package com.cloud.bank.clients.model;

import com.cloud.bank.clients.enums.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "cb_tbl_person", schema = "public")
public class PersonEntity {

    @Id
    @Size(max = 100)
    @Column("id_person")
    private Integer idPerson;

    @Size(max = 200)
    @NotNull
    @Column("name")
    private String name;

    @Column("gender")
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    @Column("age")
    private Integer age;

    @Size(max = 50)
    @NotNull
    @Column("identification")
    private String identification;

    @Size(max = 500)
    @Column("address")
    private String address;

    @Size(max = 50)
    @Column("phone")
    private String phone;

}