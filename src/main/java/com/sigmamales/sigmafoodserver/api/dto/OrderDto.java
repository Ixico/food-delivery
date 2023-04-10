package com.sigmamales.sigmafoodserver.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

    @NotNull
    private UUID id;

    @NotNull
    private BigDecimal totalPrice;

    @NotNull
    private Instant creationDate;

    @NotEmpty
    private List<@NotNull @Valid OrderProductDto> orderProducts;

    private AddressDto address;
}
