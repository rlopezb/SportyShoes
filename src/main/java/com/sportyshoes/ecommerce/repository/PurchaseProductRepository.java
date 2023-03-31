package com.sportyshoes.ecommerce.repository;

import com.sportyshoes.ecommerce.entity.PurchaseProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseProductRepository extends JpaRepository<PurchaseProduct, Long> {
}
