package com.operis.user.account.mapper;

import com.operis.user.account.domain.UserAccount;
import com.operis.user.account.repository.UserAccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserAccountMapper {

    @Mapping(target = "password", ignore = true)
    UserAccount toUserAccountEntity(UserAccountEntity userAccountEntity);

    UserAccountEntity toUserAccountEntity(UserAccount userAccount);
}
