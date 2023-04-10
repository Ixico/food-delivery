package com.sigmamales.sigmafoodserver.api.mapper;

import com.sigmamales.sigmafoodserver.api.dto.OrderDto;
import com.sigmamales.sigmafoodserver.database.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = OrderProductMapper.class
)
public interface OrderMapper {

    OrderDto toDto(Order order);


}
