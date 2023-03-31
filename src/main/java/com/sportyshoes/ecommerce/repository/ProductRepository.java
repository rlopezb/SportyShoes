package com.sportyshoes.ecommerce.repository;

import com.sportyshoes.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
