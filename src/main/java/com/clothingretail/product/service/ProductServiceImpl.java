package com.clothingretail.product.service;

import com.clothingretail.product.entity.Brand;
import com.clothingretail.product.entity.Category;
import com.clothingretail.product.entity.Product;
import com.clothingretail.product.exception.BrandNotFoundException;
import com.clothingretail.product.exception.CategoryNotFoundException;
import com.clothingretail.product.exception.ProductNotFoundException;
import com.clothingretail.product.mapper.ProductMapper;
import com.clothingretail.product.model.request.CreateProductRequest;
import com.clothingretail.product.model.request.UpdateProductRequest;
import com.clothingretail.product.model.request.UpdateProductStatusRequest;
import com.clothingretail.product.model.response.ProductDetailResponse;
import com.clothingretail.product.model.response.ProductResponse;
import com.clothingretail.product.repository.BrandRepository;
import com.clothingretail.product.repository.CategoryRepository;
import com.clothingretail.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

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

    @Override
    @Transactional(readOnly = true)
    @Cacheable(
            value = "products",
            key = "#productId"
    )
    public ProductDetailResponse getProductById(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductNotFoundException(productId)
                );

        return productMapper.toDetailResponse(product);
    }

    @Override
    public Page<ProductResponse> getProducts(Pageable pageable) {
        return productRepository
                .findAll(pageable)
                .map(productMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(
            value = "products",
            key = "#productId"
    )
    public ProductResponse updateProduct(UUID productId, UpdateProductRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductNotFoundException(productId)
                );

        Brand brand = brandRepository.findById(request.brandId())
                .orElseThrow(() ->
                        new BrandNotFoundException(request.brandId())
                );

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() ->
                        new CategoryNotFoundException(request.categoryId())
                );

        productMapper.updateEntity(
                product,
                request,
                brand,
                category
        );

        return productMapper.toResponse(product);
    }

    @Override
    @Transactional
    @CacheEvict(
            value = "products",
            key = "#productId"
    )
    public void updateProductStatus(UUID productId, UpdateProductStatusRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductNotFoundException(productId)
                );

        product.setStatus(request.status());
    }
}
