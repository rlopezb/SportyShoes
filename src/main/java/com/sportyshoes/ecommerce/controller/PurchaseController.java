package com.sportyshoes.ecommerce.controller;

import com.sportyshoes.ecommerce.dto.ProductDto;
import com.sportyshoes.ecommerce.dto.PurchaseDto;
import com.sportyshoes.ecommerce.dto.UserDto;
import com.sportyshoes.ecommerce.service.ProductService;
import com.sportyshoes.ecommerce.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Scope("session")
@SessionAttributes({"purchase", "user"})
@RequestMapping("purchase")
public class PurchaseController {
  @Autowired
  PurchaseService purchaseService;
  @Autowired
  ProductService productService;
  @Autowired
  UserDto userDto;
  @Autowired
  PurchaseDto purchaseDto;

  @ModelAttribute("user")
  public UserDto getUser() {
    return userDto;
  }


  @ModelAttribute("purchase")
  public PurchaseDto getPurchase() {
    return purchaseDto;
  }

  @GetMapping("list")
  public String purchase(Model model, @ModelAttribute("user") UserDto userDto, @ModelAttribute("purchase") PurchaseDto purchaseDto){
    purchaseDto.setUserId(userDto.getId());
    purchaseDto = purchaseService.save(purchaseDto);
    model.addAttribute("purchase",purchaseDto);
    model.addAttribute("products",productService.findAll());
    return "/purchase/list";
  }

  @GetMapping("{purchaseId}/add/{productId}")
  public String addProduct(@ModelAttribute("purchase") PurchaseDto purchaseDto,@ModelAttribute("user") UserDto userDto, @ModelAttribute("product") ProductDto productDto, @PathVariable Long purchaseId, @PathVariable Long productId) {
    purchaseService.addProduct(purchaseId, productId);
    return "redirect:/purchase/list";
  }
}
