package com.sportyshoes.ecommerce.service;

import com.sportyshoes.ecommerce.dto.UserDto;
import com.sportyshoes.ecommerce.entity.User;
import com.sportyshoes.ecommerce.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
  @Autowired
  UserRepository userRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  private ModelMapper modelMapper;

  public UserDto findById(Long id) {
    Optional<User> optionalUser = userRepository.findById(id);
    if (optionalUser.isPresent()) {
      return modelMapper.map(optionalUser.get(), UserDto.class);
    } else {
      return null;
    }
  }

  public UserDto findByUsername(String username) {
    Optional<User> optionalUser = userRepository.findByUsername(username);
    if (optionalUser.isPresent()) {
      return modelMapper.map(optionalUser.get(), UserDto.class);
    } else {
      return null;
    }
  }

  public UserDto login(String username, String password) {
    Optional<User> optionalUser = userRepository.findByUsername(username);
    if (optionalUser.isPresent() && passwordEncoder.matches(password, optionalUser.get().getPassword())) {
      return modelMapper.map(optionalUser.get(), UserDto.class);
    } else {
      return null;
    }
  }

  public UserDto save(UserDto userDto) {
    userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
    try {
      User user = userRepository.save(modelMapper.map(userDto, User.class));
      return modelMapper.map(user, UserDto.class);
    } catch (Exception ex) {
      return null;
    }
  }

  public UserDto signUp(UserDto userDto) {
    userDto.setRole("user");
    return this.save(userDto);
  }
  public void delete(Long id){
    userRepository.deleteById(id);
  }
}
