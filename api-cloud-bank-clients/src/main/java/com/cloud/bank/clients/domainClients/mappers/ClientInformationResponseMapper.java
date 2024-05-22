package com.cloud.bank.clients.domainClients.mappers;

import com.cloud.bank.clients.domainClients.dto.ClientInformationResponse;
import com.cloud.bank.clients.model.PersonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClientInformationResponseMapper {
    ClientInformationResponseMapper INSTANCE = Mappers.getMapper(ClientInformationResponseMapper.class);

    ClientInformationResponse toClientInformationResponse(PersonEntity personEntity);
}
