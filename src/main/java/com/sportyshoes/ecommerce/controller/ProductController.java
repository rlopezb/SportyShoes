package com.sportyshoes.ecommerce.controller;

import com.sportyshoes.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("product")
public class ProductController {
  @Autowired
  ProductService productService;;

  @GetMapping("list")
  private String list(Model model){
    model.addAttribute("products",productService.findAll());
    return "/product/list";
  }
}
