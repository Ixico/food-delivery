package com.sigmamales.sigmafoodserver.database.model;

import com.sigmamales.sigmafoodserver.api.request.UserRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank
    @Column(unique = true)
    private String email;

    @ToString.Exclude
    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "address_id")
    @Cascade(CascadeType.ALL)
    private Address address;

    @NotNull
    private Boolean enabled;

    @OneToOne(mappedBy = "user")
    private ActivationToken activationToken;

    @NotNull
    private Boolean locked;

    @NotNull
    private Integer loginAttempts;

    private Instant lockTimestamp;

    public void updateWith(@NotNull UserRequest request) {
        name = request.getName();
        surname = request.getSurname();
        phoneNumber = request.getPhoneNumber();
        address.updateWith(request.getAddress());
    }

    public Integer incrementAndGetLoginAttempts() {
        return ++loginAttempts;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
