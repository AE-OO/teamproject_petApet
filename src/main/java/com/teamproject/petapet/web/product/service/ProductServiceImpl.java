package com.teamproject.petapet.web.product.service;

import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

import com.teamproject.petapet.domain.product.ProductType;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 박채원 22.10.09 작성
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getProductList() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> getProductPage(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> getProductListByReview(Pageable pageable) {
        return productRepository.findAllOrderByReviewCount(pageable);
    }

    @Override
    public void updateProductStatus(String selectStatus, Long productStock, Long productId) {
        productRepository.updateProductStatus(selectStatus, productStock, productId);
    }

    @Override
    public List<Product> findAllByProductDiv(ProductType productType) {
        return productRepository.findAllByProductDiv(productType);
    }

    @Override
    public void updateProductStatusOutOfStock(List<String> productId) {
        for(String id : productId){
            productRepository.updateProductStatus("재고없음", 0L, Long.valueOf(id));
        }
    }

    @Override
    public void updateProductRating(Long productId) {
        productRepository.updateProductRating(productId);
    }

    @Override
    public Product findOne(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void productSave(Product product) {
        productRepository.save(product);
    }

    @Override
    @Transactional
    public Product findProductWithReview(Long id) {
        return productRepository.findProductWithReview(id);
    }

    @Override
    public void addProductReport(Long productId) {
        productRepository.addProductReport(productId);
    }
}
