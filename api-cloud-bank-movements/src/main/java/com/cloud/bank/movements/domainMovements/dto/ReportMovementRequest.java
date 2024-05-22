package com.cloud.bank.movements.domainMovements.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReportMovementRequest {
    Integer idClient;
    Date dateStart;
    Date dateEnd;
}
