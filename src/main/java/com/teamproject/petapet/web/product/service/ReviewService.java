package com.teamproject.petapet.web.product.service;

import com.teamproject.petapet.domain.product.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReviewService {

    Long countReviewByProduct(Long productId);

    Optional<Review> findById(Long reviewId);

    void save(Review review);

    void deleteReview(Long reviewId);
    Slice<Review> requestMoreReview(@Param("id") Long id, Pageable pageable);

    boolean existByReviewHistory(Long productId, String memberId);

    Optional<Review> findOneByMemId(Long productId, String memberId);
}
