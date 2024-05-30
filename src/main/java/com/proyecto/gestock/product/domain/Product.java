package com.proyecto.gestock.product.domain;

import com.proyecto.gestock.category.domain.Category;
import com.proyecto.gestock.orderitem.domain.OrderItem;
import com.proyecto.gestock.supplier.domain.Supplier;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 6, max = 60)
    @Column(nullable = false)
    private String name;

    @NotNull
    @Size(max = 1000)
    @Column(nullable = false)
    private String description;

    @NotNull
    @Positive(message = "Price must be greater than 0.0")
    @Column(nullable = false)
    private BigDecimal price;

    @NotNull
    @Column(nullable = false)
    private Boolean available = true;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();



    public void addStock(int amount) {
        if (amount < 0)
            throw new IllegalArgumentException("Amount must be positive");
        this.stock += amount;
    }

    public void subtractStock(int amount) {
        if (amount < 0)
            throw new IllegalArgumentException("Amount must be positive");
        if (this.stock < amount)
            throw new IllegalArgumentException("Insufficient stock to subtract");
        this.stock -= amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", category=" + (category != null ? category.getId() : "null") +
                ", supplier=" + (supplier != null ? supplier.getId() : "null") +
                '}';
    }
}
