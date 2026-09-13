package com.clothingretail.product.repository;

import com.clothingretail.product.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ColorRepository extends JpaRepository<Color, UUID> {
    boolean existsByCodeIgnoreCase(String code);
}
