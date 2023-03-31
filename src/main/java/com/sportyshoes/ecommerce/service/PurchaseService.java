package com.sportyshoes.ecommerce.service;

import com.sportyshoes.ecommerce.dto.PurchaseDto;
import com.sportyshoes.ecommerce.entity.Product;
import com.sportyshoes.ecommerce.entity.Purchase;
import com.sportyshoes.ecommerce.entity.PurchaseProduct;
import com.sportyshoes.ecommerce.repository.ProductRepository;
import com.sportyshoes.ecommerce.repository.PurchaseProductRepository;
import com.sportyshoes.ecommerce.repository.PurchaseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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


    PurchaseProduct purchaseProduct = new PurchaseProduct();
    purchaseProduct.setProduct(product);
    purchaseProduct.setPurchase(purchase);
    if (purchase.getPurchaseProducts() == null) {
      purchase.setPurchaseProducts(new ArrayList<>());
    } else {
      List<PurchaseProduct> purchaseProducts = purchase.getPurchaseProducts();
      if (purchaseProducts.contains(purchaseProduct)) {
        purchaseProduct = purchaseProducts.get(purchaseProducts.indexOf(purchaseProduct));
        purchaseProduct.setQuantity(product.getQuantity() + 1);
      } else {
        purchaseProduct.setQuantity(1);
        purchaseProducts.add(purchaseProduct);
      }
    }
    product.setQuantity(product.getQuantity() - 1);

    purchaseProductRepository.save(purchaseProduct);
    purchase = purchaseRepository.save(purchase);
    productRepository.save(product);

    return modelMapper.map(purchase, PurchaseDto.class);
  }

  public PurchaseDto save(PurchaseDto purchaseDto) {
    try {
      Purchase purchase = purchaseRepository.save(modelMapper.map(purchaseDto, Purchase.class));
      return modelMapper.map(purchase, PurchaseDto.class);
    } catch (Exception ex) {
      return null;
    }
  }
}
