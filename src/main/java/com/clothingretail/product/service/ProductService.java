package com.clothingretail.product.service;

import com.clothingretail.product.model.request.CreateProductRequest;
import com.clothingretail.product.model.request.UpdateProductRequest;
import com.clothingretail.product.model.request.UpdateProductStatusRequest;
import com.clothingretail.product.model.response.ProductDetailResponse;
import com.clothingretail.product.model.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductService {
    ProductResponse createProduct(CreateProductRequest request);

    ProductDetailResponse getProductById(UUID productId);

    Page<ProductResponse> getProducts(Pageable pageable);

    ProductResponse updateProduct(
            UUID productId,
            UpdateProductRequest request
    );

    void updateProductStatus(
            UUID productId,
            UpdateProductStatusRequest request
    );
}
