package com.clothingretail.product.exception;

import java.util.UUID;

public class SizeNotFoundException extends RuntimeException {
    public SizeNotFoundException(UUID sizeId) {
        super("Size not found: " + sizeId);
    }
}
