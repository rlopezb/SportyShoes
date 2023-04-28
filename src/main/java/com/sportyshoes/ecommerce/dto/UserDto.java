package com.sportyshoes.ecommerce.dto;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope("session")
public class UserDto {
  private Long id;
  private String name;
  private String lastname;
  private String email;
  private String username;
  private String password;
  private String role;
}
