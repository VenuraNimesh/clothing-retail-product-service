package com.clothingretail.product.service;

import com.clothingretail.product.entity.Brand;
import com.clothingretail.product.entity.Category;
import com.clothingretail.product.entity.Product;
import com.clothingretail.product.exception.BrandNotFoundException;
import com.clothingretail.product.exception.ProductNotFoundException;
import com.clothingretail.product.mapper.ProductMapper;
import com.clothingretail.product.model.request.CreateProductRequest;
import com.clothingretail.product.model.request.UpdateProductStatusRequest;
import com.clothingretail.product.model.response.ProductResponse;
import com.clothingretail.product.repository.BrandRepository;
import com.clothingretail.product.repository.CategoryRepository;
import com.clothingretail.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock private ProductRepository productRepository;
    @Mock private BrandRepository brandRepository;
    @Mock private CategoryRepository categoryRepository;
    @Mock private ProductMapper productMapper;

    private ProductServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new ProductServiceImpl(productRepository, brandRepository, categoryRepository, productMapper);
    }

    @Test
    void createProductPersistsAndReturnsResponse() {
        UUID brandId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        CreateProductRequest request = new CreateProductRequest(brandId, categoryId, "Shirt", "Blue shirt");
        Brand brand = Brand.builder().id(brandId).name("Brand").build();
        Category category = Category.builder().id(categoryId).name("Tops").build();
        Product product = Product.builder().brand(brand).category(category).name("Shirt").description("Blue shirt").build();
        Product saved = Product.builder().id(UUID.randomUUID()).brand(brand).category(category).name("Shirt").description("Blue shirt").build();
        ProductResponse response = mock(ProductResponse.class);

        when(brandRepository.findById(brandId)).thenReturn(Optional.of(brand));
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(productMapper.toEntity(request, brand, category)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(saved);
        when(productMapper.toResponse(saved)).thenReturn(response);

        assertSame(response, service.createProduct(request));
        verify(productRepository).save(product);
    }

    @Test
    void createProductThrowsWhenBrandDoesNotExist() {
        UUID brandId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        CreateProductRequest request = new CreateProductRequest(brandId, categoryId, "Shirt", null);
        when(brandRepository.findById(brandId)).thenReturn(Optional.empty());

        assertThrows(BrandNotFoundException.class, () -> service.createProduct(request));
        verify(productRepository, never()).save(any());
    }

    @Test
    void getProductByIdThrowsWhenMissing() {
        UUID productId = UUID.randomUUID();
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> service.getProductById(productId));
    }

    @Test
    void updateProductStatusChangesStatus() {
        UUID productId = UUID.randomUUID();
        Product product = Product.builder().id(productId).status(Product.Status.ACTIVE).build();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        service.updateProductStatus(productId, new UpdateProductStatusRequest(Product.Status.INACTIVE));

        assertEquals(Product.Status.INACTIVE, product.getStatus());
    }
}
