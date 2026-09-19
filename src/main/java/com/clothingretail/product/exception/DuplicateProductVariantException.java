package com.clothingretail.product.exception;

import java.util.UUID;

public class DuplicateProductVariantException extends RuntimeException {
    public DuplicateProductVariantException(
            UUID productId,
            UUID sizeId,
            UUID colorId
    ) {
        super(
                "Product variant already exists for product: "
                        + productId
                        + ", size: "
                        + sizeId
                        + ", color: "
                        + colorId
        );
    }
}
