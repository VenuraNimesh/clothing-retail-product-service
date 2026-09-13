package com.clothingretail.product.repository;

import com.clothingretail.product.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SizeRepository extends JpaRepository<Size, UUID> {
    boolean existsByCodeIgnoreCase(String code);
}
