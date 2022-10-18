package com.teamproject.petapet.web.product.service;

import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.domain.product.ProductType;

import java.util.List;

public interface ProductService {

    List<Product> findAllByProductDiv(ProductType productType);

    Product findOne(Long id);

    Product productSave(Product product);
}