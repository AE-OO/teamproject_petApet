package com.teamproject.petapet.web.product.service;

import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.domain.product.ProductType;
import java.util.List;

/**
 * 박채원 22.10.09 작성
 */

public interface ProductService {
    List<Product> getProductList();
    void updateProductStatus(String selectStatus, Long productId);
    List<Product> findAllByProductDiv(ProductType productType);

    Product findOne(Long id);

    Product productSave(Product product);
}

