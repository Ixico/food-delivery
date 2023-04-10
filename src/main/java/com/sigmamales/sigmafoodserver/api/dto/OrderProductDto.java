package com.sigmamales.sigmafoodserver.api.dto;

import jakarta.validation.constraints.NotBlank;
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
public class OrderProductDto {

    @NotNull
    private UUID id;

    @NotBlank
    private String name;

    @NotNull
    private Integer quantity;

}
