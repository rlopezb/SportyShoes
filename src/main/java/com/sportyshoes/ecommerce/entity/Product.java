package com.sportyshoes.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String brand;
  private String model;
  private Integer size;
  private Float price;
  private Integer quantity;
  @OneToMany(mappedBy = "product")
  private List<PurchaseProduct> purchaseProducts=new ArrayList<>();
  @ManyToOne
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

}

