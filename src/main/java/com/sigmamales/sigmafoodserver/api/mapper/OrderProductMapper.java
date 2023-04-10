package com.sigmamales.sigmafoodserver.api.mapper;

import com.sigmamales.sigmafoodserver.api.dto.OrderProductDto;
import com.sigmamales.sigmafoodserver.database.model.OrderProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface OrderProductMapper {

    @Mapping(source = "product.name", target = "name")
    OrderProductDto toDto(OrderProduct orderProduct);
}
