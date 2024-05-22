package com.cloud.bank.movements.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;
import java.util.TimeZone;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseResponse {
    @Builder.Default
    String timestamp = ZonedDateTime.now(TimeZone.getDefault().toZoneId()).toString();
    String message;
    String code;
}
