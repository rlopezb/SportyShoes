package com.sportyshoes.ecommerce.dto;

import com.sportyshoes.ecommerce.entity.Product;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope("session")
public class PurchaseProductDto {
  private Long id;
  private Long purchaseId;
  private Product product;
  private Integer quantity;
}
