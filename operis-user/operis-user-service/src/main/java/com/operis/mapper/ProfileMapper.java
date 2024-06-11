package com.operis.mapper;

import com.operis.dto.ProfileDTO;
import com.operis.model.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    @Mapping(source = "user.id", target = "userId")
    ProfileDTO toDto(Profile profile);

    @Mapping(source = "userId", target = "user.id")
    Profile toEntity(ProfileDTO profileDTO);
}
