package com.clothingretail.product.model.response;

import com.clothingretail.product.entity.ProductVariant;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ProductVariantResponse(
        UUID id,

        UUID productId,

        String sku,

        UUID sizeId,

        UUID colorId,

        BigDecimal price,

        String currency,

        ProductVariant.Status status,

        Instant createdAt,

        Instant updatedAt
) {
}
