package com.sigmamales.sigmafoodserver.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderSummaryDto {

    @NotNull
    private BigDecimal productsCost;

    @NotNull
    private BigDecimal deliveryCost;

    @NotNull
    private BigDecimal totalPrice;
}
