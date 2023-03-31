package com.sportyshoes.ecommerce.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
  @GetMapping("index")
  private String index(){
    return "index";
  }
}
