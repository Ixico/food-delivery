package com.sigmamales.sigmafoodserver.controller;


import com.sigmamales.sigmafoodserver.api.dto.ProductDto;
import com.sigmamales.sigmafoodserver.api.mapper.ProductMapper;
import com.sigmamales.sigmafoodserver.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(ProductController.BASE_PATH)
@Transactional
public class ProductController {

    public static final String BASE_PATH = "/product";

    private final ProductService productService;

    private final ProductMapper productMapper;


    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts().stream().map(productMapper::toDto).collect(Collectors.toList());
    }

}
