package com.sigmamales.sigmafoodserver.api.request;

import com.sigmamales.sigmafoodserver.service.ValidationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    @Pattern(regexp = ValidationService.PHONE_NUMBER_REGEX, message = "Correct format: 111222333")
    private String phoneNumber;

    @NotNull
    @Valid
    private AddressRequest address;
}
