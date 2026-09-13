package com.clothingretail.product.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(
        name = "sizes",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_sizes_code",
                        columnNames = "code"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Size extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(
            name = "name",
            nullable = false,
            length = 50
    )
    private String name;

    @Column(
            name = "code",
            nullable = false,
            length = 20
    )
    private String code;
}
