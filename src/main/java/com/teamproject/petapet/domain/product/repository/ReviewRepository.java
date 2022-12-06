package com.teamproject.petapet.domain.product.repository;

import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.domain.product.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * 22.10.20 쿼리 작성. By.OH
 */
@Transactional
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 리뷰 평균점수 계산
    @Query(value = "select sum(a.reviewRating)/(select count(*) from Review where productId = ?1) from Review a where productId = ?1",nativeQuery = true)
    Long avg(@Param("id") Long id);

    Long countReviewByProduct(Product product);

    @Query(value = "select a from Review a where a.product.productId = :id")
    Slice<Review> requestMoreReview(@Param("id")Long id, Pageable pageable);
}

