package com.cloud.bank.clients.domainClients.mappers;

import com.cloud.bank.clients.domainClients.dto.ClientCreateRequest;
import com.cloud.bank.clients.model.PersonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mappings({
        @Mapping(target = "idPerson", ignore = true)
    })
    PersonEntity toPersonEntity(ClientCreateRequest personEntity);
}
