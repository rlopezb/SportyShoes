package com.sportyshoes.ecommerce.controller;

import com.sportyshoes.ecommerce.dto.CategoryDto;
import com.sportyshoes.ecommerce.dto.ProductDto;
import com.sportyshoes.ecommerce.dto.UserDto;
import com.sportyshoes.ecommerce.service.CategoryService;
import com.sportyshoes.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("product")
public class ProductController {
  @Autowired
  ProductService productService;
  ;
  @Autowired
  CategoryService categoryService;

  @GetMapping("list")
  private String list(Model model, @ModelAttribute("user") UserDto userDto) {
    model.addAttribute("user", userDto);
    List<ProductDto> products = productService.findAll();
    if (products != null) {
      model.addAttribute("products", products);
      return "/product/list";
    } else {
      model.addAttribute("message", "Cannot get list of products");
      return "/error";
    }
  }

  @GetMapping("delete/{id}")
  private String delete(Model model, RedirectAttributes redirectAttributes, @ModelAttribute("user") UserDto userDto, @PathVariable Long id) {
    productService.delete(id);
    model.addAttribute("user", userDto);
    redirectAttributes.addFlashAttribute("message", "The product with id " + id + " was deleted correctly");
    model.addAttribute("products", productService.findAll());
    return "redirect:/product/list";
  }

  @GetMapping("add")
  private String getAdd(Model model, @ModelAttribute("user") UserDto userDto, @ModelAttribute("product") ProductDto productDto) {
    model.addAttribute("user", userDto);
    List<CategoryDto> categories = categoryService.findAll();
    if (categories != null) {
      model.addAttribute("categories", categoryService.findAll());
      return "/product/add";
    } else {
      model.addAttribute("message", "Cannot get list of categories");
      return "/error";
    }
  }

  @PostMapping("add")
  private String postAdd(Model model, RedirectAttributes redirectAttributes, @ModelAttribute("user") UserDto userDto, @ModelAttribute("product") ProductDto productDto) {
    productDto = productService.save(productDto);
    if (productDto != null) {
      model.addAttribute("user", userDto);
      model.addAttribute("products", productService.findAll());
      redirectAttributes.addFlashAttribute("message", "The product with id " + productDto.getId() + " was added correctly");
      return "redirect:/product/list";
    } else {
      model.addAttribute("message", "Cannot add product");
      return "/error";
    }
  }


  @GetMapping("update/{id}")
  private String getUpdate(Model model, @ModelAttribute("user") UserDto userDto, @PathVariable Long id) {
    ProductDto productDto = productService.findById(id);
    if (productDto != null) {
      model.addAttribute("user", userDto);
      model.addAttribute("product", productDto);
      model.addAttribute("categories", categoryService.findAll());
      return "/product/update";
    } else {
      model.addAttribute("message", "Cannot update product with id " + id);
      return "/error";
    }
  }

  @PostMapping("update")
  private String postUpdate(Model model, RedirectAttributes redirectAttributes, @ModelAttribute("user") UserDto userDto, @ModelAttribute("product") ProductDto productDto) {
    productDto = productService.save(productDto);
    if (productDto != null) {
      model.addAttribute("user", userDto);
      model.addAttribute("products", productService.findAll());
      redirectAttributes.addFlashAttribute("message", "The product with id " + productDto.getId() + " was modified correctly");
      return "redirect:/product/list";
    } else {
      model.addAttribute("message", "Cannot update product");
      return "/error";
    }
  }
}
