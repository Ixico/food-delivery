package com.sigmamales.sigmafoodserver.api.controller;

import com.sigmamales.sigmafoodserver.api.dto.OrderDto;
import com.sigmamales.sigmafoodserver.api.dto.OrderSummaryDto;
import com.sigmamales.sigmafoodserver.api.mapper.OrderMapper;
import com.sigmamales.sigmafoodserver.api.request.OrderRequest;
import com.sigmamales.sigmafoodserver.api.request.OrderSummaryRequest;
import com.sigmamales.sigmafoodserver.service.OrderService;
import com.sigmamales.sigmafoodserver.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(OrderController.BASE_PATH)
@Transactional
public class OrderController {

    public static final String BASE_PATH = "/order";

    private final OrderService orderService;

    private final OrderMapper orderMapper;

    private final UserService userService;


    @PostMapping
    public OrderDto createOrder(@NotNull @Valid @RequestBody OrderRequest orderRequest) {
        return orderMapper.toDto(
                orderService.createOrder(orderRequest, userService.getCurrentUser())
        );
    }

    @GetMapping
    public List<OrderDto> getAllUserOrders() {
        return orderService.getAllUserOrders(userService.getCurrentUser()).stream()
                .map(orderMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping("/summary")
    public OrderSummaryDto orderSummary(@NotNull @Valid @RequestBody OrderSummaryRequest orderSummaryRequest) {
        return orderService.orderSummary(orderSummaryRequest);
    }
}
