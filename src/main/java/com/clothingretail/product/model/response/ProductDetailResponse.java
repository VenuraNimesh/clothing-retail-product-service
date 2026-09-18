package com.clothingretail.product.model.response;

import com.clothingretail.product.entity.Product;

import java.time.Instant;
import java.util.UUID;

public record ProductDetailResponse(
        UUID id,

        BrandResponse brand,

        CategoryResponse category,

        String name,

        String description,

        Product.Status status,

        Instant createdAt,

        Instant updatedAt
) {

    public record BrandResponse(
            UUID id,
            String name
    ) {
    }

    public record CategoryResponse(
            UUID id,
            String name
    ) {
    }
}
