package com.clothingretail.product.repository;

import com.clothingretail.product.entity.ProductVariant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, UUID> {
    boolean existsBySkuIgnoreCase(String sku);

    List<ProductVariant> findByProductId(UUID productId);

    boolean existsBySkuIgnoreCaseAndIdNot(
            String sku,
            UUID variantId
    );

    Page<ProductVariant> findByProductId(
            UUID productId,
            Pageable pageable
    );

    boolean existsByProductIdAndSizeIdAndColorId(
            UUID productId,
            UUID sizeId,
            UUID colorId
    );

    boolean existsByProductIdAndSizeIdAndColorIdAndIdNot(
            UUID productId,
            UUID sizeId,
            UUID colorId,
            UUID variantId
    );
}
