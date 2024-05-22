package com.cloud.bank.movements.domainMovements.dto;

import com.cloud.bank.movements.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportMovementResponse extends BaseResponse {
    List<ReportMovementDetailResponse> movements;
}
