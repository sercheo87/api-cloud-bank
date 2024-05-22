package com.cloud.bank.clients.domainClients.dto;

import com.cloud.bank.clients.enums.Gender;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientInformationResponse {

    @NotNull
    @NotEmpty
    String name;

    @NotNull
    Gender gender;

    @NotNull
    @Range(min = 1, max = 100)
    Integer age;

    @NotNull
    @NotEmpty
    String identification;

    @NotNull
    @NotEmpty
    String address;

    @NotNull
    String phone;

}
