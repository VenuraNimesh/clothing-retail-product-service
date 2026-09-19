package com.clothingretail.product.service;

import com.clothingretail.product.model.request.CreateProductVariantRequest;
import com.clothingretail.product.model.request.UpdateProductVariantRequest;
import com.clothingretail.product.model.request.UpdateProductVariantStatusRequest;
import com.clothingretail.product.model.response.ProductVariantResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductVariantService {
    ProductVariantResponse createVariant(
            UUID productId,
            CreateProductVariantRequest request
    );

    Page<ProductVariantResponse> getVariants(
            UUID productId,
            Pageable pageable
    );

    ProductVariantResponse getVariantById(
            UUID productId,
            UUID variantId
    );

    ProductVariantResponse updateVariant(
            UUID productId,
            UUID variantId,
            UpdateProductVariantRequest request
    );

    void updateVariantStatus(
            UUID productId,
            UUID variantId,
            UpdateProductVariantStatusRequest request
    );
}
