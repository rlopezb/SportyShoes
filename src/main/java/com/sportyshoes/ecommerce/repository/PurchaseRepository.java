package com.sportyshoes.ecommerce.repository;

import com.sportyshoes.ecommerce.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PurchaseRepository  extends JpaRepository<Purchase, Long> {

  List<Purchase> findAllByPayedNotNull();
  List<Purchase> findAllByCancelledNotNull();
  List<Purchase> findAllByPayedIsNullAndCancelledIsNull();
  List<Purchase> findAllByCreatedAfter(LocalDateTime created);
  List<Purchase> findAllByPayedNotNullAndCreatedAfter(LocalDateTime created);
  List<Purchase> findAllByCancelledNotNullAndCreatedAfter(LocalDateTime created);
  List<Purchase> findAllByPayedIsNullAndCancelledIsNullAndCreatedAfter(LocalDateTime created);

}
