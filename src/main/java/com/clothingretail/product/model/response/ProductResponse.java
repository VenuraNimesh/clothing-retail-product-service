package com.clothingretail.product.model.response;

import com.clothingretail.product.entity.Product;

import java.time.Instant;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        UUID brandId,
        UUID categoryId,
        String name,
        String description,
        Product.Status status,
        Instant createdAt,
        Instant updatedAt
) {
}
