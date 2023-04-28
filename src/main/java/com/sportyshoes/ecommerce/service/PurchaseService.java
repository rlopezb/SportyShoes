package com.sportyshoes.ecommerce.service;

import com.sportyshoes.ecommerce.dto.PurchaseDto;
import com.sportyshoes.ecommerce.dto.PurchaseProductDto;
import com.sportyshoes.ecommerce.entity.Product;
import com.sportyshoes.ecommerce.entity.Purchase;
import com.sportyshoes.ecommerce.entity.PurchaseProduct;
import com.sportyshoes.ecommerce.repository.ProductRepository;
import com.sportyshoes.ecommerce.repository.PurchaseProductRepository;
import com.sportyshoes.ecommerce.repository.PurchaseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseService {
  @Autowired
  PurchaseRepository purchaseRepository;

  @Autowired
  PurchaseProductRepository purchaseProductRepository;

  @Autowired
  ProductRepository productRepository;

  @Autowired
  private ModelMapper modelMapper;

  public PurchaseDto addProduct(Long purchaseId, Long productId) {
    Purchase purchase;
    if (purchaseId == null) {
      purchase = new Purchase();
    } else {
      Optional<Purchase> optionalPurchase = purchaseRepository.findById(purchaseId);
      if (optionalPurchase.isPresent()) {
        purchase = optionalPurchase.get();
      } else {
        return null;
      }
    }

    Product product;
    Optional<Product> optionalProduct = productRepository.findById(productId);
    if (optionalProduct.isPresent()) {
      product = optionalProduct.get();
    } else {
      return null;
    }


    if (purchase.getPurchaseProducts() == null) {
      purchase.setPurchaseProducts(new ArrayList<>());
    }
    List<PurchaseProduct> purchaseProducts = purchase.getPurchaseProducts();
    PurchaseProduct purchaseProduct = purchaseProducts.stream()
        .filter(pp -> pp.getProduct().getId() == productId)
        .findFirst()
        .orElse(null);
    if (purchaseProduct == null) {
      purchaseProduct = new PurchaseProduct();
      purchaseProduct.setProduct(product);
      purchaseProduct.setPurchase(purchase);
      purchaseProduct.setQuantity(1);
      purchaseProducts.add(purchaseProduct);
    } else {
      purchaseProduct.setQuantity(purchaseProduct.getQuantity() + 1);
    }
    product.setQuantity(product.getQuantity() - 1);

    purchaseProductRepository.save(purchaseProduct);
    purchase = purchaseRepository.save(purchase);
    productRepository.save(product);

    List<PurchaseProductDto> purchaseProductDtos = List.of(modelMapper.map(purchaseProducts, PurchaseProductDto[].class));
    PurchaseDto purchaseDto = modelMapper.map(purchase, PurchaseDto.class);
    purchaseDto.setPurchaseProducts(purchaseProductDtos);
    return purchaseDto;
  }

  public PurchaseDto save(PurchaseDto purchaseDto) {
    try {
      Purchase purchase = purchaseRepository.save(modelMapper.map(purchaseDto, Purchase.class));
      return modelMapper.map(purchase, PurchaseDto.class);
    } catch (Exception ex) {
      return null;
    }
  }

  public PurchaseDto pay(PurchaseDto purchaseDto) {
    purchaseDto.setPayed(LocalDateTime.now());
    return save(purchaseDto);
  }

  public PurchaseDto cancel(PurchaseDto purchaseDto) {
    purchaseDto.setCancelled(LocalDateTime.now());
    for (PurchaseProductDto purchaseProductDto : purchaseDto.getPurchaseProducts()) {
      Optional<Product> optionalProduct = productRepository.findById(purchaseProductDto.getProductId());
      if (optionalProduct.isPresent()) {
        Product product = optionalProduct.get();
        product.setQuantity(product.getQuantity() + purchaseProductDto.getQuantity());
        productRepository.save(product);
      }
    }
    Purchase purchase = purchaseRepository.save(modelMapper.map(purchaseDto, Purchase.class));
    return modelMapper.map(purchase, PurchaseDto.class);
  }

  public List<PurchaseDto> findAll() {
    List<Purchase> purchases = purchaseRepository.findAll();
    if (purchases == null)
      return null;
    return purchases.stream()
        .map(product -> modelMapper.map(product, PurchaseDto.class))
        .collect(Collectors.toList());
  }

  public List<PurchaseDto> findByStatusAndCreated(String status, LocalDateTime created) {
    List<Purchase> purchases = null;
    if (status == null && created == null) {
      return findAll();
    }
    if (status != null && created == null) {
      switch (status) {
        case "draft" -> purchases = purchaseRepository.findAllByPayedIsNullAndCancelledIsNull();
        case "cancelled" -> purchases = purchaseRepository.findAllByCancelledNotNull();
        case "payed" -> purchases = purchaseRepository.findAllByPayedNotNull();
      }
    }
    if (status == null && created != null) {
      purchases = purchaseRepository.findAllByCreatedAfter(created);
    }
    if (status != null && created != null) {
      switch (status) {
        case "draft" -> purchases = purchaseRepository.findAllByPayedIsNullAndCancelledIsNullAndCreatedAfter(created);
        case "cancelled" -> purchases = purchaseRepository.findAllByCancelledNotNullAndCreatedAfter(created);
        case "payed" -> purchases = purchaseRepository.findAllByPayedNotNullAndCreatedAfter(created);
      }
    }
    if (purchases == null)
      return null;
    return purchases.stream()
        .map(product -> modelMapper.map(product, PurchaseDto.class))
        .collect(Collectors.toList());
  }
}
