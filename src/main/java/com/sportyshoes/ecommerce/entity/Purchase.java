package com.sportyshoes.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private LocalDateTime created;
  private LocalDateTime payed;
  private LocalDateTime cancelled;
  @ManyToOne
  @JoinColumn(name = "userId", nullable = false)
  private User user;

  @OneToMany(mappedBy = "purchase")
  private List<PurchaseProduct> purchaseProducts=new ArrayList<>();

}
