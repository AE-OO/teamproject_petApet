package com.teamproject.petapet.domain.product.repository;

import com.teamproject.petapet.domain.product.Review;
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

    @Query("select count(r) from Review r where r.product.productId=:productId")
    Long countReviewByProduct(@Param("productId")Long productId);

    @Query(value = "select a from Review a where a.product.productId = :id")
    Slice<Review> requestMoreReview(@Param("id")Long id, Pageable pageable);
}

