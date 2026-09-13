package com.clothingretail.product.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "brands", uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_brands_name",
                columnNames = "name"
        )
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Brand extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(
            name = "name",
            nullable = false,
            length = 100
    )
    private String name;

    @Column(
            name = "description",
            length = 500
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
