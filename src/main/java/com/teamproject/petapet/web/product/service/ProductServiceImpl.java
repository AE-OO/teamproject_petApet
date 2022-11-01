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
    public void updateProductStatus(String selectStatus, Long productStock, Long productId) {
        productRepository.updateProductStatus(selectStatus, productStock, productId);
    }

    @Override
    public List<Product> findAllByProductDiv(ProductType productType){
        return productRepository.findAllByProductDiv(productType);
    }

    @Override
    public void updateProductStatusOutOfStock(List<String> productId) {
        for(String id : productId){
            productRepository.updateProductStatus("재고없음", 0L, Long.valueOf(id));
        }
    }

    @Override
    public Product findOne(Long id){
      return productRepository.findById(id).get();
    }

    @Override
    public Product productSave(Product product){
        return productRepository.save(product);
    }

    @Override
    public void addProductReport(Long productId) {
        productRepository.addProductReport(productId);
    }
}
