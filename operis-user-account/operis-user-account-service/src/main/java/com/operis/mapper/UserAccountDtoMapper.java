package com.operis.mapper;

import com.operis.dto.UserAccountDTO;
import com.operis.user.account.domain.UserAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAccountDtoMapper {

    UserAccountDTO toUserAccountDTO(UserAccount userAccount);

    UserAccount toUserAccount(UserAccountDTO userAccountDTO);
}
