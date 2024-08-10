package com.operis.mapper;

import com.operis.dto.UserAccountDTO;
import com.operis.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserAccountMapper {

    @Mapping(target = "password", ignore = true)
    UserAccountDTO toUserDTO(User user);

    User toUser(UserAccountDTO userAccountDTO);
}
