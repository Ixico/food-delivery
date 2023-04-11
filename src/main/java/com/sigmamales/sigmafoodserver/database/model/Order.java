package com.sigmamales.sigmafoodserver.database.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderProduct> orderProducts;

    @NotNull
    private Instant creationDate;

    @NotNull
    private BigDecimal totalPrice;

    @NotNull
    private BigDecimal productsCost;

    @NotNull
    private BigDecimal deliveryCost;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name = "address_id")
    @NotNull
    private Address address;
}
