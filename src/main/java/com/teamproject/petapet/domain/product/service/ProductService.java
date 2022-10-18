package com.teamproject.petapet.domain.product.service;

import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.domain.product.ProductRepository;
import com.teamproject.petapet.domain.product.ProductType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAllByProductDiv(ProductType productType){
        return productRepository.findAllByProductDiv(productType);
    }

    public Product findOne(Long id){
      return productRepository.findById(id).get();
    }

    public Product productSave(Product product){
        return productRepository.save(product);
    }
}
