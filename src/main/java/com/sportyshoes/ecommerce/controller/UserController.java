package com.sportyshoes.ecommerce.controller;

import com.sportyshoes.ecommerce.dto.UserDto;
import com.sportyshoes.ecommerce.service.UserService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@Scope("session")
@SessionAttributes("user")
public class UserController {
  @Autowired
  UserService userService;

  @Autowired
  UserDto userDto;

  @ModelAttribute("user")
  public UserDto getUser() {
    return userDto;
  }

  @GetMapping("index")
  private String index(@ModelAttribute("user") UserDto userDto) {
    return "index";
  }

  @GetMapping("user/login")
  private String login(@ModelAttribute("user") UserDto userDto) {
    return "user/login";
  }

  @GetMapping("user/signUp")
  private String signUp(@ModelAttribute("user") UserDto userDto) {
    return "user/signUp";
  }

  @PostMapping("user/login")
  private String login(Model model, UserDto userDto) {
    UserDto dbUserDto = userService.login(userDto.getName(), userDto.getPassword());
    if (dbUserDto != null) {
      model.addAttribute("message", "User '" + dbUserDto.getName() + "' logged in successfully");
      model.addAttribute("user", dbUserDto);
      return "home";
    } else {
      model.addAttribute("message", "User login failure");
      return "error";
    }
  }

  @PostMapping("user/signUp")
  private String singUp(Model model, UserDto userDto) {
    UserDto dbUserDto = userService.signUp(userDto);
    if (dbUserDto != null) {
      model.addAttribute("message", "User  '" + dbUserDto.getName() + "' signed up successfully");
      model.addAttribute("user", dbUserDto);
      return "user/signUp";
    } else {
      model.addAttribute("message", "User signup failure");
      return "error";
    }
  }

  @PostMapping("user/update")
  private String update(Model model, UserDto userDto) {
    UserDto dbUserDto = userService.save(userDto);
    if (dbUserDto != null) {
      model.addAttribute("message", "User '" + dbUserDto.getName() + "' updated successfully");
      model.addAttribute("user", dbUserDto);
      return "user/update";
    } else {
      model.addAttribute("message", "User update failure");
      return "error";
    }
  }

  @GetMapping("user/delete/{id}")
  private String update(Model model, @PathParam("id") Long id) {
    UserDto dbUserDto = userService.findById(id);
    if (dbUserDto != null) {
      userService.delete(id);
      model.addAttribute("message", "User '" + userDto.getEmail() + "' deleted successful");
      return "user/delete";
    } else {
      model.addAttribute("message", "User delete failure");
      return "error";
    }
  }
}