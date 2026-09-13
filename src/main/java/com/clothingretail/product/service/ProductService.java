package com.clothingretail.product.service;

import com.clothingretail.product.model.request.CreateProductRequest;
import com.clothingretail.product.model.response.ProductResponse;

public interface ProductService {
    ProductResponse createProduct(CreateProductRequest request);
}
