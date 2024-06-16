package com.operis.mapper;

import com.operis.dto.ProfileDTO;
import com.operis.model.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ProfileMapper {

    ProfileDTO toDto(Profile profile);

    Profile toEntity(ProfileDTO profileDTO);
}
