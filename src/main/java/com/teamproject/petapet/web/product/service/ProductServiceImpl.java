package com.teamproject.petapet.web.product.service;

import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.domain.product.repository.ProductRepository;
import com.teamproject.petapet.web.product.productdtos.ProductDTO;
import lombok.RequiredArgsConstructor;

import com.teamproject.petapet.domain.product.ProductType;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 박채원 22.10.09 작성
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<ProductDTO> getProductList(String companyId) {
        List<Product> productList = productRepository.findAllByCompany_CompanyId(companyId);
        List<ProductDTO> productDTOList = productList.stream().map(list -> ProductDTO.fromEntityForManageProduct(list)).collect(Collectors.toList());
        return productDTOList;
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
    public void updateProductInfo(String type, Long productId, Long productStock, String productStatus) {
        if(type.equals("stock")){
            productRepository.updateProductStock(productStock, productId);
        }else if(type.equals("status")){
            productRepository.updateProductStatus(productStatus, productId);
        }
    }

    @Override
    public Page<Product> findAllByProductDiv(ProductType productType,Pageable pageable) {
        return productRepository.findAllByProductDiv(productType,pageable);
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
    public Optional<Product> findOne(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public void productSave(Product product) {
        productRepository.save(product);
    }

    @Override
    @Transactional
    public Optional<Product> findProductWithReview(Long id) {
        return productRepository.findProductWithReview(id);
    }

    @Override
    public void addProductReport(Long productId) {
        productRepository.addProductReport(productId);
    }
}
