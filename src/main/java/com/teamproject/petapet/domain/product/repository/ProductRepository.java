package com.teamproject.petapet.domain.product.repository;

import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.domain.product.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Modifying
    @Transactional
    @Query("update Product p set p.productStatus =:selectStatus, p.productStock =:productStock where p.productId =:productId")
    void updateProductStatus(@Param("selectStatus") String selectStatus, @Param("productStock") Long productStock, @Param("productId") Long productId);

    List<Product> findAllByProductDiv(ProductType productType);
    @Query("select a from Product a,Review b where a.productId=:id and b.product.productId=:id")
    Product findProductWithReview(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("update Product p set p.productReport = p.productReport + 1 where p.productId =:productId")
    void addProductReport(@Param("productId") Long productId);
}
