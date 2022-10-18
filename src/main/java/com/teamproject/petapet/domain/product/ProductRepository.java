package com.teamproject.petapet.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Modifying
    @Transactional
    @Query("update Product p set p.productStatus =:selectStatus where p.productId =:productId")
    void updateProductStatus(String selectStatus, Long productId);

    List<Product> findAllByProductDiv(ProductType productType);
}
