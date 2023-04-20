package com.sigmamales.sigmafoodserver.api.request;

import com.sigmamales.sigmafoodserver.service.ValidationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAccountRequest {

    @NotBlank
    @Email(regexp = ValidationService.EMAIL_REGEX)
    private String email;

    @NotBlank
    private String password;

    @NotNull
    @Valid
    private UserRequest userData;
}
