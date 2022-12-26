package com.teamproject.petapet.domain.product.repository;

import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.domain.product.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Modifying
    @Transactional
    @Query("update Product p set p.productStatus =:selectStatus, p.productStock =:productStock where p.productId =:productId")
    void updateProductStatus(@Param("selectStatus") String selectStatus, @Param("productStock") Long productStock, @Param("productId") Long productId);

    @Modifying
    @Transactional
    @Query("update Product p set p.productStock =:productStock where p.productId =:productId")
    void updateProductStock(Long productStock, Long productId);

    @Modifying
    @Transactional
    @Query("update Product p set p.productStatus =:productStatus where p.productId =:productId")
    void updateProductStatus(String productStatus, Long productId);
    List<Product> findAllByCompany_CompanyId(String companyId);
    Page<Product> findAllByProductDiv(ProductType productType, Pageable pageable);
    Page<Product> findAllByProductNameContainsAndProductDiv(String productName, ProductType productType, Pageable pageable);
    @Modifying
    @Transactional
    @Query("update Product p set p.productReviewCount=:reviewCount where p.productId=:productId")
    void updateProductReviewCount(@Param("productId") Long productId, @Param("reviewCount")Long reviewCount);
    @Query("select a from Product a,Review b where a.productId=:id and b.product.productId=:id")
    Optional<Product> findProductWithReview(@Param("id") Long id);

    @Query("select p from Product p order by p.review.size desc")
    Page<Product> findAllOrderByReviewCount(Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "update Product a set a.productRating=(select sum(b.reviewRating)/(select count(*) from Review where productId = ?1) from Review b where b.productId = ?1) where a.productId = ?1", nativeQuery = true)
    void updateProductRating(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("update Product p set p.productReport = p.productReport + 1 where p.productId =:productId")
    void addProductReport(@Param("productId") Long productId);
}
