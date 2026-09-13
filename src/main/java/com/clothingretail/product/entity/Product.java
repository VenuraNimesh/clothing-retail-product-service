package com.clothingretail.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import java.util.UUID;

@Entity
@Table(
        name = "products",
        indexes = {
                @Index(
                        name = "idx_products_brand_id",
                        columnList = "brand_id"
                ),
                @Index(
                        name = "idx_products_category_id",
                        columnList = "category_id"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "brand_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_products_brand")
    )
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "category_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_products_category")
    )
    private Category category;

    @Column(
            name = "name",
            nullable = false,
            length = 200
    )
    private String name;

    @Column(
            name = "description",
            columnDefinition = "TEXT"
    )
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            nullable = false,
            length = 20
    )
    @Builder.Default
    private Status status = Status.ACTIVE;

    public enum Status {
        ACTIVE,
        INACTIVE
    }
}
