package com.sigmamales.sigmafoodserver.api.mapper;

import com.sigmamales.sigmafoodserver.api.dto.AddressDto;
import com.sigmamales.sigmafoodserver.api.request.AddressRequest;
import com.sigmamales.sigmafoodserver.database.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AddressMapper {

    AddressDto toDto(Address address);

    @Mapping(target = "id", ignore = true)
    Address toEntity(AddressRequest addressRequest);

}
