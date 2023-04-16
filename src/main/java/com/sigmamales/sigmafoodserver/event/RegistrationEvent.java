package com.sigmamales.sigmafoodserver.event;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationEvent {

    @NotBlank
    private String email;

    @NotBlank
    private String token;

}
