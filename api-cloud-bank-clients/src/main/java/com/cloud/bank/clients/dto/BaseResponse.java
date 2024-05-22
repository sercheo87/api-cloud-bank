package com.cloud.bank.clients.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.TimeZone;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseResponse {
    @Builder.Default
    String timestamp = ZonedDateTime.now(TimeZone.getDefault().toZoneId()).toString();
    String message;
    String code;
}
