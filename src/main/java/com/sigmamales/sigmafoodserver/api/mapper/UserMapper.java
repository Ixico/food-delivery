package com.sigmamales.sigmafoodserver.api.mapper;

import com.sigmamales.sigmafoodserver.api.dto.UserDto;
import com.sigmamales.sigmafoodserver.database.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {

    UserDto toDto(User user);
}
