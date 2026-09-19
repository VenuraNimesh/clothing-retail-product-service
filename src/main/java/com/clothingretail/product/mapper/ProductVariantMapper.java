package com.clothingretail.product.mapper;

import com.clothingretail.product.entity.Color;
import com.clothingretail.product.entity.Product;
import com.clothingretail.product.entity.ProductVariant;
import com.clothingretail.product.entity.Size;
import com.clothingretail.product.model.request.CreateProductVariantRequest;
import com.clothingretail.product.model.request.UpdateProductVariantRequest;
import com.clothingretail.product.model.response.ProductVariantResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductVariantMapper {
    public ProductVariant toEntity(
            CreateProductVariantRequest request,
            Product product,
            Size size,
            Color color
    ) {
        return ProductVariant.builder()
                .product(product)
                .sku(request.sku())
                .size(size)
                .color(color)
                .price(request.price())
                .currency(request.currency())
                .build();
    }

    public ProductVariantResponse toResponse(
            ProductVariant variant
    ) {
        return new ProductVariantResponse(
                variant.getId(),
                variant.getProduct().getId(),
                variant.getSku(),
                variant.getSize().getId(),
                variant.getColor().getId(),
                variant.getPrice(),
                variant.getCurrency(),
                variant.getStatus(),
                variant.getCreatedAt(),
                variant.getUpdatedAt()
        );
    }

    public void updateEntity(
            ProductVariant variant,
            UpdateProductVariantRequest request,
            Size size,
            Color color
    ) {
        variant.setSku(request.sku());
        variant.setSize(size);
        variant.setColor(color);
        variant.setPrice(request.price());
        variant.setCurrency(request.currency());
    }
}
