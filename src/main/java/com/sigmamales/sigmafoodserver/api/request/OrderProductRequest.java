package com.sigmamales.sigmafoodserver.api.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProductRequest {

    @NotNull
    private UUID productId;

    @NotNull
    private Integer quantity;

}