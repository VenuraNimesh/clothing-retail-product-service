package com.clothingretail.product.service;

import com.clothingretail.product.entity.Brand;
import com.clothingretail.product.entity.Category;
import com.clothingretail.product.entity.Product;
import com.clothingretail.product.exception.BrandNotFoundException;
import com.clothingretail.product.exception.CategoryNotFoundException;
import com.clothingretail.product.mapper.ProductMapper;
import com.clothingretail.product.model.request.CreateProductRequest;
import com.clothingretail.product.model.response.ProductResponse;
import com.clothingretail.product.repository.BrandRepository;
import com.clothingretail.product.repository.CategoryRepository;
import com.clothingretail.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public ProductResponse createProduct(CreateProductRequest request) {
        Brand brand = brandRepository.findById(request.brandId())
                .orElseThrow(() ->
                        new BrandNotFoundException(request.brandId()));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() ->
                        new CategoryNotFoundException(request.categoryId()));

        Product product = productMapper.toEntity(request, brand, category);
        Product savedProduct = productRepository.save(product);

        return productMapper.toResponse(savedProduct);
    }
}
