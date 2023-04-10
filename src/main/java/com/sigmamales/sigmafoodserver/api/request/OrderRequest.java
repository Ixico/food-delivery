package com.sigmamales.sigmafoodserver.api.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    @NotEmpty
    private List<@NotNull @Valid OrderProductRequest> orderProductRequests;

    @NotNull
    @Valid
    private AddressRequest addressRequest;
}
