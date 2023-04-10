package com.sigmamales.sigmafoodserver.api.mapper;

import com.sigmamales.sigmafoodserver.api.dto.ProductDto;
import com.sigmamales.sigmafoodserver.database.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProductMapper {

    ProductDto toDto(Product product);

}
