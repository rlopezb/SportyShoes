package com.sportyshoes.ecommerce.service;

import com.sportyshoes.ecommerce.dto.CategoryDto;
import com.sportyshoes.ecommerce.entity.Category;
import com.sportyshoes.ecommerce.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {
  @Autowired
  CategoryRepository categoryRepository;

  @Autowired
  private ModelMapper modelMapper;

  public List<CategoryDto> findAll() {
    List<Category> categories = categoryRepository.findAll();
    return categories.stream()
        .map(category -> modelMapper.map(category, CategoryDto.class))
        .collect(Collectors.toList());
  }

  public CategoryDto findById(Long id) {
    Optional<Category> optionalCategory = categoryRepository.findById(id);
    return optionalCategory.map(category -> modelMapper.map(category, CategoryDto.class)).orElse(null);
  }

  public CategoryDto save(CategoryDto categoryDto) {
    try {
      Category category = categoryRepository.save(modelMapper.map(categoryDto, Category.class));
      return modelMapper.map(category, CategoryDto.class);
    } catch (Exception ex) {
      return null;
    }
  }

  public void delete(Long id) {
    categoryRepository.deleteById(id);
  }

}
