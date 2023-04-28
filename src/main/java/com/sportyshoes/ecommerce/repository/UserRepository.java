package com.sportyshoes.ecommerce.repository;

import com.sportyshoes.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByName(String name);

  Optional<User> findByUsername(String username);

  Long deleteUserById(Long id);

  List<User> findByUsernameContaining(String pattern);
}
