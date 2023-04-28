package com.sportyshoes.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchase_product")
public class PurchaseProduct {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  @JoinColumn(name = "purchase_id", nullable = false)
  private Purchase purchase;
  @ManyToOne
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;
  private Integer quantity;
}
