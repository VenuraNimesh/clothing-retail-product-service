package com.clothingretail.product.entity;

import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(
        name = "product_variants",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_product_variants_sku",
                        columnNames = "sku"
                ),
                @UniqueConstraint(
                        name = "uk_product_variants_product_size_color",
                        columnNames = {
                                "product_id",
                                "size_id",
                                "color_id"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariant extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "product_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_product_variants_product")
    )
    private Product product;

    @Column(
            name = "sku",
            nullable = false,
            length = 100
    )
    private String sku;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "size_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_product_variants_size")
    )
    private Size size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "color_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_product_variants_color")
    )
    private Color color;

    @Column(
            name = "price",
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal price;

    @Column(
            name = "currency",
            nullable = false,
            length = 3
    )
    private String currency;

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
