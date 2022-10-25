package com.teamproject.petapet.web.product.service;

import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;

import com.teamproject.petapet.domain.product.ProductType;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 박채원 22.10.09 작성
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Override
    public List<Product> getProductList() {
        return productRepository.findAll();
    }

    @Override
    public void updateProductStatus(String selectStatus, Long productId) {
        productRepository.updateProductStatus(selectStatus, productId);
    }

    @Override
    public List<Product> findAllByProductDiv(ProductType productType){
        return productRepository.findAllByProductDiv(productType);
    }

    @Override
    public Product findOne(Long id){
      return productRepository.findById(id).get();
    }

    @Override
    public Product productSave(Product product){
        return productRepository.save(product);
    }
}
