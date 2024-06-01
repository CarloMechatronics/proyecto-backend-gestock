package com.proyecto.gestock.brand.domain;

import com.proyecto.gestock.product.domain.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Brand {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(max = 20)
    @Column(unique = true, nullable = false)
    private String name;

    @NotEmpty
    @Column(unique = true, nullable = false)
    private String logo = "https://cdn4.iconfinder.com/data/icons/core-ui-outlined/32/outlined_placeholder-256.png";

    @NotNull
    @Column(nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;
}
