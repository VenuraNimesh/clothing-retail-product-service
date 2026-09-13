package com.clothingretail.product.repository;

import com.clothingretail.product.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductImageRepository extends JpaRepository<ProductImage, UUID> {
    List<ProductImage> findByProductIdOrderByDisplayOrderAsc(UUID productId);
    boolean existsByProductIdAndPrimaryTrue(UUID productId);
}
