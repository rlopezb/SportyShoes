package com.sportyshoes.ecommerce.dto;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope("session")
public class PurchaseProductDto {
  private Long id;
  private Long purchaseId;
  private Long productId;
  private String productBrand;
  private String productModel;
  private String productCategoryName;
  private Integer productSize;
  private Float productPrice;
  private Integer quantity;
}
