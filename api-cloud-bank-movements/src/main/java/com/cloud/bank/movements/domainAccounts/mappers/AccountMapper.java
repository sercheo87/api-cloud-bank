package com.cloud.bank.movements.domainAccounts.mappers;

import com.cloud.bank.movements.domainAccounts.dto.CreateAccountRequest;
import com.cloud.bank.movements.model.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mappings({
        @Mapping(target = "idAccount", ignore = true),
        @Mapping(target = "accountTypeEnum", expression = "java(com.cloud.bank.movements.enums.AccountType.fromString(createAccountRequest.getAccountType().toValue()))"),
        @Mapping(target = "accountType", expression = "java(createAccountRequest.getAccountType().toValue())")
    })
    AccountEntity toAccountEntity(CreateAccountRequest createAccountRequest);
}
