package com.clothingretail.product.service;

import com.clothingretail.product.entity.Color;
import com.clothingretail.product.entity.Product;
import com.clothingretail.product.entity.ProductVariant;
import com.clothingretail.product.entity.Size;
import com.clothingretail.product.exception.DuplicateSkuException;
import com.clothingretail.product.exception.ProductVariantNotFoundException;
import com.clothingretail.product.mapper.ProductVariantMapper;
import com.clothingretail.product.model.request.CreateProductVariantRequest;
import com.clothingretail.product.model.response.ProductVariantResponse;
import com.clothingretail.product.repository.ColorRepository;
import com.clothingretail.product.repository.ProductRepository;
import com.clothingretail.product.repository.ProductVariantRepository;
import com.clothingretail.product.repository.SizeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductVariantServiceImplTest {

    @Mock private ProductVariantRepository variantRepository;
    @Mock private ProductRepository productRepository;
    @Mock private SizeRepository sizeRepository;
    @Mock private ColorRepository colorRepository;
    @Mock private ProductVariantMapper mapper;

    private ProductVariantServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new ProductVariantServiceImpl(variantRepository, productRepository, sizeRepository, colorRepository, mapper);
    }

    @Test
    void createVariantPersistsAndReturnsResponse() {
        UUID productId = UUID.randomUUID();
        UUID sizeId = UUID.randomUUID();
        UUID colorId = UUID.randomUUID();
        CreateProductVariantRequest request = new CreateProductVariantRequest("SKU-001", sizeId, colorId, new BigDecimal("29.99"), "USD");
        Product product = Product.builder().id(productId).build();
        Size size = Size.builder().id(sizeId).code("M").build();
        Color color = Color.builder().id(colorId).code("BLU").build();
        ProductVariant variant = ProductVariant.builder().product(product).sku("SKU-001").size(size).color(color).build();
        ProductVariant saved = ProductVariant.builder().id(UUID.randomUUID()).product(product).sku("SKU-001").size(size).color(color).build();
        ProductVariantResponse response = mock(ProductVariantResponse.class);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(sizeRepository.findById(sizeId)).thenReturn(Optional.of(size));
        when(colorRepository.findById(colorId)).thenReturn(Optional.of(color));
        when(variantRepository.existsBySkuIgnoreCase("SKU-001")).thenReturn(false);
        when(variantRepository.existsByProductIdAndSizeIdAndColorId(productId, sizeId, colorId)).thenReturn(false);
        when(mapper.toEntity(request, product, size, color)).thenReturn(variant);
        when(variantRepository.save(variant)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        assertSame(response, service.createVariant(productId, request));
        verify(variantRepository).save(variant);
    }

    @Test
    void createVariantThrowsForDuplicateSku() {
        UUID productId = UUID.randomUUID();
        UUID sizeId = UUID.randomUUID();
        UUID colorId = UUID.randomUUID();
        CreateProductVariantRequest request = new CreateProductVariantRequest("SKU-001", sizeId, colorId, new BigDecimal("29.99"), "USD");
        Product product = Product.builder().id(productId).build();
        Size size = Size.builder().id(sizeId).build();
        Color color = Color.builder().id(colorId).build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(sizeRepository.findById(sizeId)).thenReturn(Optional.of(size));
        when(colorRepository.findById(colorId)).thenReturn(Optional.of(color));
        when(variantRepository.existsBySkuIgnoreCase("SKU-001")).thenReturn(true);

        assertThrows(DuplicateSkuException.class, () -> service.createVariant(productId, request));
        verify(variantRepository, never()).save(any());
    }

    @Test
    void getVariantByIdRejectsVariantFromAnotherProduct() {
        UUID productId = UUID.randomUUID();
        UUID otherProductId = UUID.randomUUID();
        UUID variantId = UUID.randomUUID();
        Product requestedProduct = Product.builder().id(productId).build();
        Product otherProduct = Product.builder().id(otherProductId).build();
        ProductVariant variant = ProductVariant.builder().id(variantId).product(otherProduct).build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(requestedProduct));
        when(variantRepository.findById(variantId)).thenReturn(Optional.of(variant));

        assertThrows(ProductVariantNotFoundException.class, () -> service.getVariantById(productId, variantId));
    }
}
