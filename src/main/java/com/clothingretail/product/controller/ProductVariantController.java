package com.clothingretail.product.controller;

import com.clothingretail.product.model.request.CreateProductVariantRequest;
import com.clothingretail.product.model.request.UpdateProductVariantRequest;
import com.clothingretail.product.model.request.UpdateProductVariantStatusRequest;
import com.clothingretail.product.model.response.ProductVariantResponse;
import com.clothingretail.product.service.ProductVariantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products/{productId}/variants")
@RequiredArgsConstructor
public class ProductVariantController {
    private final ProductVariantService productVariantService;

    @PostMapping
    public ResponseEntity<ProductVariantResponse> createVariant(
            @PathVariable UUID productId,
            @Valid @RequestBody CreateProductVariantRequest request
    ) {
        ProductVariantResponse response =
                productVariantService.createVariant(
                        productId,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Page<ProductVariantResponse>> getVariants(
            @PathVariable UUID productId,
            @PageableDefault(
                    size = 20,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                productVariantService.getVariants(
                        productId,
                        pageable
                )
        );
    }

    @GetMapping("/{variantId}")
    public ResponseEntity<ProductVariantResponse> getVariantById(
            @PathVariable UUID productId,
            @PathVariable UUID variantId
    ) {

        return ResponseEntity.ok(
                productVariantService.getVariantById(
                        productId,
                        variantId
                )
        );
    }

    @PutMapping("/{variantId}")
    public ResponseEntity<ProductVariantResponse> updateVariant(
            @PathVariable UUID productId,
            @PathVariable UUID variantId,
            @Valid @RequestBody UpdateProductVariantRequest request
    ) {

        return ResponseEntity.ok(
                productVariantService.updateVariant(
                        productId,
                        variantId,
                        request
                )
        );
    }

    @PatchMapping("/{variantId}/status")
    public ResponseEntity<Void> updateVariantStatus(
            @PathVariable UUID productId,
            @PathVariable UUID variantId,
            @Valid @RequestBody
            UpdateProductVariantStatusRequest request
    ) {

        productVariantService.updateVariantStatus(
                productId,
                variantId,
                request
        );

        return ResponseEntity.noContent().build();
    }
}
