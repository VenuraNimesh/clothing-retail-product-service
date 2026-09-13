package com.clothingretail.product.mapper;

import com.clothingretail.product.entity.Brand;
import com.clothingretail.product.entity.Category;
import com.clothingretail.product.entity.Product;
import com.clothingretail.product.model.request.CreateProductRequest;
import com.clothingretail.product.model.response.ProductResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(
            CreateProductRequest request,
            Brand brand,
            Category category
    ) {
        return Product.builder()
                .brand(brand)
                .category(category)
                .name(request.name())
                .description(request.description())
                .build();
    }

    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getBrand().getId(),
                product.getCategory().getId(),
                product.getName(),
                product.getDescription(),
                product.getStatus(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
