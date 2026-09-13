package com.clothingretail.product.repository;

import com.clothingretail.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findByParentId(UUID parentId);

    boolean existsByParentIdAndNameIgnoreCase(
            UUID parentId,
            String name
    );
}
