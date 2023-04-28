package com.sportyshoes.ecommerce.controller;

import com.sportyshoes.ecommerce.dto.PurchaseDto;
import com.sportyshoes.ecommerce.dto.UserDto;
import com.sportyshoes.ecommerce.service.ProductService;
import com.sportyshoes.ecommerce.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Optional;

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

  @GetMapping("do")
  public String purchase(Model model, @ModelAttribute("user") UserDto userDto, @ModelAttribute("purchase") PurchaseDto purchaseDto) {
    if (purchaseDto.getUser() == null || purchaseDto.getUser().getId() == null) {
      purchaseDto.setUser(userDto);
      purchaseDto = purchaseService.save(purchaseDto);
    }
    model.addAttribute("user", userDto);
    model.addAttribute("purchase", purchaseDto);
    model.addAttribute("products", productService.findAll());
    return "/purchase/do";
  }

  @GetMapping("list")
  public String purchase(Model model, @ModelAttribute("user") UserDto userDto, @RequestParam Optional<LocalDateTime> created, @RequestParam Optional<String> status) {
    String statusValue = null;
    if (status.isPresent() && !status.get().isEmpty()) {
      statusValue = status.get();
    }
    LocalDateTime createdValue = null;
    if (created.isPresent()) {
      createdValue = created.get();
    }
    model.addAttribute("user", userDto);
    model.addAttribute("purchases", purchaseService.findByStatusAndCreated(statusValue, createdValue));
    return "/purchase/list";
  }

  @GetMapping("{purchaseId}/add/{productId}")
  public String addProduct(Model model, RedirectAttributes redirectAttributes, @ModelAttribute("user") UserDto userDto, @PathVariable Long purchaseId, @PathVariable Long productId) {
    PurchaseDto purchaseDto = purchaseService.addProduct(purchaseId, productId);
    model.addAttribute("user", userDto);
    model.addAttribute("purchase", purchaseDto);
    model.addAttribute("products", productService.findAll());
    redirectAttributes.addFlashAttribute("message", "Product with id " + productId + " added to your order number " + purchaseId);
    return "redirect:/purchase/do";
  }

  @GetMapping("{purchaseId}/pay")
  public String pay(Model model, RedirectAttributes redirectAttributes, @ModelAttribute("user") UserDto userDto, @ModelAttribute("purchase") PurchaseDto purchaseDto, @PathVariable Long purchaseId) {
    purchaseService.pay(purchaseDto);
    purchaseDto = new PurchaseDto();
    purchaseDto.setUser(userDto);
    purchaseDto = purchaseService.save(purchaseDto);
    model.addAttribute("user", userDto);
    model.addAttribute("purchase", purchaseDto);
    model.addAttribute("products", productService.findAll());
    redirectAttributes.addFlashAttribute("message", "Your order with number " + purchaseId + " is confirmed");
    return "redirect:/userHome";
  }

  @GetMapping("{purchaseId}/cancel")
  public String cancel(Model model, RedirectAttributes redirectAttributes, @ModelAttribute("user") UserDto userDto, @ModelAttribute("purchase") PurchaseDto purchaseDto, @PathVariable Long purchaseId) {
    purchaseService.cancel(purchaseDto);
    purchaseDto = new PurchaseDto();
    purchaseDto.setUser(userDto);
    purchaseDto = purchaseService.save(purchaseDto);
    model.addAttribute("user", userDto);
    model.addAttribute("purchase", purchaseDto);
    model.addAttribute("products", productService.findAll());
    redirectAttributes.addFlashAttribute("message", "Your order with number " + purchaseId + " is cancelled");
    return "redirect:/userHome";
  }
}
