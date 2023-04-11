package com.sigmamales.sigmafoodserver.database.model;

import com.sigmamales.sigmafoodserver.api.request.AddressRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank
    private String city;

    @NotBlank
    private String street;

    @NotBlank
    private String number;

    public void updateWith(@NotNull AddressRequest addressRequest) {
        city = addressRequest.getCity();
        street = addressRequest.getStreet();
        number = addressRequest.getNumber();
    }
}
