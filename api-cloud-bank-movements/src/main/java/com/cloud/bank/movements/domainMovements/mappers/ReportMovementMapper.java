package com.cloud.bank.movements.domainMovements.mappers;

import com.cloud.bank.movements.domainMovements.dto.ReportMovementDetailResponse;
import com.cloud.bank.movements.model.ReportMovementsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReportMovementMapper {
    ReportMovementMapper INSTANCE = Mappers.getMapper(ReportMovementMapper.class);

    @Mappings({
        @Mapping(target = "dateMovement", expression = "java(new java.text.SimpleDateFormat(\"yy-MM-dd HH:mm:ss\").format(java.util.Date.from(reportMovementsEntity.getDateMovement().atZone(java.time.ZoneId.systemDefault()).toInstant())))"),
        @Mapping(target = "amount", expression = "java(\"Deposito\".equals(reportMovementsEntity.getTypeMovement()) ? \"+\" + reportMovementsEntity.getAmount().toString() : \"-\" + reportMovementsEntity.getAmount().toString())")
    })
    ReportMovementDetailResponse toReportMovementDetailResponse(ReportMovementsEntity reportMovementsEntity);

}
