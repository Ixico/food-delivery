package com.sigmamales.sigmafoodserver.service;

import com.sigmamales.sigmafoodserver.api.request.OrderRequest;
import com.sigmamales.sigmafoodserver.database.model.Address;
import com.sigmamales.sigmafoodserver.database.model.Order;
import com.sigmamales.sigmafoodserver.database.model.OrderProduct;
import com.sigmamales.sigmafoodserver.database.model.User;
import com.sigmamales.sigmafoodserver.database.repository.AddressRepository;
import com.sigmamales.sigmafoodserver.database.repository.OrderProductRepository;
import com.sigmamales.sigmafoodserver.database.repository.OrderRepository;
import com.sigmamales.sigmafoodserver.database.repository.ProductRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    private final OrderProductRepository orderProductRepository;

    private final AddressRepository addressRepository;


    public Order createOrder(@NotNull @Valid OrderRequest orderRequest, @NotNull User user) {
        var addressRequest = orderRequest.getAddressRequest();
        var address = addressRepository.save(
                Address.builder()
                        .city(addressRequest.getCity())
                        .street(addressRequest.getStreet())
                        .number(addressRequest.getNumber())
                        .build()
        );
        var order = Order.builder()
                .creationDate(Instant.now())
                .user(user)
                .address(address)
                .build();
        var orderProducts = orderRequest.getOrderProductRequests().stream().map(request ->
                OrderProduct.builder()
                        .product(productRepository.getById(request.getProductId()))
                        .order(order)
                        .quantity(request.getQuantity())
                        .build()
        ).collect(Collectors.toList());
        order.setTotalPrice(calculateTotalPrice(orderProducts));
        var savedOrder = orderRepository.save(order);
        savedOrder.setOrderProducts(orderProductRepository.saveAll(orderProducts));
        log.debug("Created order: {}", savedOrder);
        return savedOrder;
    }

    private BigDecimal calculateTotalPrice(@NotNull List<OrderProduct> orderProducts) {
        return orderProducts.stream()
                .map(orderProduct -> orderProduct.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(orderProduct.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
