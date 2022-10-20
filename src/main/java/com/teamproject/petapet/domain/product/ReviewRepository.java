package com.teamproject.petapet.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 22.10.20 쿼리 작성. By.OH
 */
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 리뷰 평균점수 계산
    @Query(value = "select sum(a.reviewRating)/(select count(*) from Review where productId = ?1) from Review a where productId = ?1",nativeQuery = true)
    Long avg(@Param("id") Long id);
}

