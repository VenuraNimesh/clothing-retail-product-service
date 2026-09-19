package com.clothingretail.product.exception;

import java.util.UUID;

public class ColorNotFoundException extends RuntimeException {
    public ColorNotFoundException(UUID colorId) {
        super("Color not found: " + colorId);
    }
}
