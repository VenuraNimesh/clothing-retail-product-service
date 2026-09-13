package com.clothingretail.product.repository;

import com.clothingretail.product.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BrandRepository extends JpaRepository<Brand, UUID> {
    boolean existsByNameIgnoreCase(String name);
}
