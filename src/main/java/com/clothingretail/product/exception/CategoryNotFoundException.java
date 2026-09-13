package com.clothingretail.product.exception;

import java.util.UUID;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(UUID categoryId) {
        super("Category not found "+ categoryId);
    }
}
