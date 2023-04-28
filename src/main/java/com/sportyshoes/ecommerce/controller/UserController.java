package com.sportyshoes.ecommerce.controller;

import com.sportyshoes.ecommerce.dto.UserDto;
import com.sportyshoes.ecommerce.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@Scope("session")
@SessionAttributes("user")
@RequestMapping("user")
public class UserController {
  @Autowired
  UserService userService;

  @Autowired
  UserDto userDto;

  @ModelAttribute("user")
  public UserDto getUser() {
    return userDto;
  }

  @GetMapping("login")
  private String login(@ModelAttribute("user") UserDto userDto) {
    return "/user/login";
  }

  @GetMapping("signUp")
  private String signUp(@ModelAttribute("user") UserDto userDto) {
    return "/user/signUp";
  }

  @GetMapping({"list"})
  private String list(Model model, @ModelAttribute("user") UserDto userDto, @RequestParam Optional<String> pattern) {
    model.addAttribute("user", userDto);
    List<UserDto> users = null;
    if (pattern.isEmpty()) {
      users = userService.findAll();
    } else {
      users = userService.findByUsernameContaining(pattern.get());
    }
    if (users != null) {
      model.addAttribute("user", userDto);
      model.addAttribute("users", users);
      return "/user/list";
    } else {
      model.addAttribute("message", "Cannot get list of users");
      return "/error";
    }
  }

  @PostMapping("login")
  private String login(Model model, UserDto userDto) {
    UserDto dbUserDto = userService.login(userDto.getUsername(), userDto.getPassword());
    if (dbUserDto != null) {
      model.addAttribute("message", "User '" + dbUserDto.getName() + "' logged in successfully");
      model.addAttribute("user", dbUserDto);
      if (dbUserDto.getRole().equals("admin")) {
        return "/adminHome";
      } else {
        return "/userHome";
      }
    } else {
      model.addAttribute("message", "User login failure");
      return "/error";
    }
  }

  @PostMapping("signUp")
  private String singUp(Model model, UserDto userDto) {
    UserDto dbUserDto = userService.signUp(userDto);
    if (dbUserDto != null) {
      model.addAttribute("message", "User  '" + dbUserDto.getName() + "' signed up successfully");
      model.addAttribute("user", dbUserDto);
      return "/user/signUp";
    } else {
      model.addAttribute("message", "User signup failure");
      return "/error";
    }
  }

  @GetMapping("logout")
  public String logout(HttpSession session) {
    userDto=null;
    session.removeAttribute("user");
    session.invalidate();
    return "redirect:/index";  //Where you go after logout here.
  }

//  @PostMapping("update")
//  private String update(Model model, UserDto userDto) {
//    UserDto dbUserDto = userService.save(userDto);
//    if (dbUserDto != null) {
//      model.addAttribute("message", "User '" + dbUserDto.getName() + "' updated successfully");
//      model.addAttribute("user", dbUserDto);
//      return "/user/update";
//    } else {
//      model.addAttribute("message", "User update failure");
//      return "/error";
//    }
//  }
//
//  @GetMapping("delete/{id}")
//  private String update(Model model, @PathParam("id") Long id) {
//    UserDto dbUserDto = userService.findById(id);
//    if (dbUserDto != null) {
//      userService.delete(id);
//      model.addAttribute("message", "User '" + userDto.getEmail() + "' deleted successful");
//      return "/user/delete";
//    } else {
//      model.addAttribute("message", "User delete failure");
//      return "/error";
//    }
//  }
}