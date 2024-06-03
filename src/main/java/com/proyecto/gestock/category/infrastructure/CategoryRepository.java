package com.proyecto.gestock.category.infrastructure;

import com.proyecto.gestock.category.domain.Category;
import com.proyecto.gestock.category.dto.CategoryDisplay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<CategoryDisplay> findAllByIdNotNull();
    List<CategoryDisplay> findAllByNameContains(String namePart);
    // When user clicks a category name or logo and then retrieve all products in service
    Optional<Category> findByName(String name);
}
