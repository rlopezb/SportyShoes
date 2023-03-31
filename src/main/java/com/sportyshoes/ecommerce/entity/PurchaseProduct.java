package com.sportyshoes.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseProduct {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  @JoinColumn(name = "purchaseId", nullable = false)
  private Purchase purchase;
  @ManyToOne
  @JoinColumn(name = "productId", nullable = false)
  private Product product;
  private Integer quantity;
}
