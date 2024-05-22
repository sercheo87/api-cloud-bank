package com.cloud.bank.movements.domainMovements.mappers;

import com.cloud.bank.movements.domainMovements.dto.CreateMovementRequest;
import com.cloud.bank.movements.model.MovementEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MovementMapper {
    MovementMapper INSTANCE = Mappers.getMapper(MovementMapper.class);

    @Mappings({
        @Mapping(target = "idMovement", ignore = true),
        @Mapping(target = "dateMovement", ignore = true),
        @Mapping(target = "idAccount", ignore = true),
        @Mapping(target = "balance", ignore = true),
        @Mapping(target = "typeMovementEnum", expression = "java(com.cloud.bank.movements.enums.MovementType.fromString(createMovementRequest.getTypeMovement().toValue()))"),
        @Mapping(target = "typeMovement", expression = "java(createMovementRequest.getTypeMovement().toValue())")
    })
    MovementEntity toMovementEntity(CreateMovementRequest createMovementRequest);
}
