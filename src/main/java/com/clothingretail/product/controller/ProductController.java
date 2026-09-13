package com.clothingretail.product.controller;

import com.clothingretail.product.model.request.CreateProductRequest;
import com.clothingretail.product.model.response.ProductResponse;
import com.clothingretail.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {
        ProductResponse response =
                productService.createProduct(request);
        return ResponseEntity
                .status(HttpStatus.CREATED).body(response);
    }

}
