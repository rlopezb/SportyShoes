package com.sportyshoes.ecommerce.service;

import com.sportyshoes.ecommerce.dto.ProductDto;
import com.sportyshoes.ecommerce.entity.Product;
import com.sportyshoes.ecommerce.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
  @Autowired
  ProductRepository productRepository;

  @Autowired
  private ModelMapper modelMapper;

  public List<ProductDto> findAll() {
    List<Product> products = productRepository.findAll();
    return products.stream()
        .map(product -> modelMapper.map(product, ProductDto.class))
        .collect(Collectors.toList());
  }

  public ProductDto findById(Long id) {
    Optional<Product> optionalProduct = productRepository.findById(id);
    return optionalProduct.map(product -> modelMapper.map(product, ProductDto.class)).orElse(null);
  }

  public ProductDto save(ProductDto productDto) {
    try {
      Product product = productRepository.save(modelMapper.map(productDto, Product.class));
      return modelMapper.map(product, ProductDto.class);
    } catch (Exception ex) {
      return null;
    }
  }

  public void delete(Long id) {
    productRepository.deleteById(id);
  }

}
