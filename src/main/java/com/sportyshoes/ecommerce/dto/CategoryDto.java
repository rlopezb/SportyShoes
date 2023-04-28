package com.sportyshoes.ecommerce.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class CategoryDto {
  private Long id;
  private String name;
  private List<ProductDto> products;
}
