package com.operis.mapper;

import com.operis.dto.UserAccountDTO;
import com.operis.model.UserAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserAccountMapper {

    @Mapping(target = "password", ignore = true)
    UserAccountDTO toUserAccountDTO(UserAccount userAccount);

    UserAccount toUserAccount(UserAccountDTO userAccountDTO);
}
