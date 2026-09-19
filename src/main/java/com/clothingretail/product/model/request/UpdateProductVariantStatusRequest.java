package com.clothingretail.product.model.request;

import com.clothingretail.product.entity.ProductVariant;
import jakarta.validation.constraints.NotNull;

public record UpdateProductVariantStatusRequest(
        @NotNull(message = "Variant status is required")
        ProductVariant.Status status
) {
}
