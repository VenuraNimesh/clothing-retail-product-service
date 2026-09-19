package com.clothingretail.product.exception;

import java.util.UUID;

public class ProductVariantNotFoundException extends RuntimeException {
    public ProductVariantNotFoundException(UUID variantId) {
        super("Product variant not found: " + variantId);
    }
}
