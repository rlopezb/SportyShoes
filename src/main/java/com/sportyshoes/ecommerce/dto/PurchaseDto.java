package com.sportyshoes.ecommerce.dto;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Component
@Scope("session")
public class PurchaseDto {
  private Long id;
  private LocalDateTime created = LocalDateTime.now();
  private LocalDateTime payed;
  private LocalDateTime cancelled;
  private UserDto user;
  private List<PurchaseProductDto> purchaseProducts;

}
