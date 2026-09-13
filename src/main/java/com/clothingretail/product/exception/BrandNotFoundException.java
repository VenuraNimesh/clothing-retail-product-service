package com.clothingretail.product.exception;

import java.util.UUID;

public class BrandNotFoundException extends RuntimeException {
    public BrandNotFoundException(UUID brandId) {
        super("Brand not found: " + brandId);
    }
}
