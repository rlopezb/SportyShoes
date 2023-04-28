package com.sportyshoes.ecommerce.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
  @GetMapping("adminHome")
  private String adminHome(){
    return "adminHome";
  }
  @GetMapping("userHome")
  private String userHome(){
    return "userHome";
  }
  @GetMapping("index")
  private String index(){
    return "index";
  }
}
