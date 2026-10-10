package com.clothingretail.product.model.request;

import com.clothingretail.product.entity.Product;
import jakarta.validation.constraints.NotNull;

public record UpdateProductStatusRequest(
        @NotNull(message = "Product status is required")
        Product.Status status
) {
}
