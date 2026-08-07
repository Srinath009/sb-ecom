package com.ecommerce.project.repository;

import com.ecommerce.project.entity.Category;
import com.ecommerce.project.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByCategoryOrderByPriceAsc(Category category, Pageable pageable);
    Page<Product> findByProductNameLikeIgnoreCase(String keyword, Pageable pageable);
    List<Product> findByProductNameLikeIgnoreCase(String productName);
}
