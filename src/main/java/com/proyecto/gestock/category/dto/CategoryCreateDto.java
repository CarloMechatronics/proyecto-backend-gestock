package com.proyecto.gestock.category.dto;

import com.proyecto.gestock.product.domain.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryCreateDto {
    @NotBlank
    @Size(min = 2, max = 20)
    private String name;

    private String imageUrl;

    private Boolean active;

    private List<Product> products;
}
