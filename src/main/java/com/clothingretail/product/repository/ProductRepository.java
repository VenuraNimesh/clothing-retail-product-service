package com.clothingretail.product.repository;

import com.clothingretail.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByBrandId(UUID brandId);

    List<Product> findByCategoryId(UUID categoryId);
}
