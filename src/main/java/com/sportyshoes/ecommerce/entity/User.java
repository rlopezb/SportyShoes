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
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String lastname;
  @Column(unique = true)
  private String email;
  @Column(unique = true)
  private String username;
  private String password;
  private String role;
  @OneToMany(mappedBy = "user")
  private List<Purchase> purchases = new ArrayList<>();
}

