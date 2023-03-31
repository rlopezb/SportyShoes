package com.sportyshoes.ecommerce.configuration;

import com.sportyshoes.ecommerce.dto.PurchaseDto;
import com.sportyshoes.ecommerce.dto.UserDto;
import com.sportyshoes.ecommerce.entity.Purchase;
import com.sportyshoes.ecommerce.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(new PropertyMap<User, UserDto>() {
      @Override
      protected void configure() {
        skip(destination.getPassword());
      }
    });
//    PropertyMap<Purchase, PurchaseDto> warehouseFieldMapping = new PropertyMap<Purchase, PurchaseDto>() {
//      protected void configure() {
//
//      }
//    };
    return modelMapper;
  }
}
