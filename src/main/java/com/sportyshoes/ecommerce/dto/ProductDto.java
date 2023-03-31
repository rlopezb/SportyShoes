package com.sportyshoes.ecommerce.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ProductDto {
  private Long id;
  private String brand;
  private String model;
  private Integer size;
  private Float price;
  private Integer quantity;
}
