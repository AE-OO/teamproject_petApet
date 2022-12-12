package com.teamproject.petapet.web.product.service;

import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.domain.product.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.query.Param;

public interface ReviewService {

    Long countReviewByProduct(Long productId);

    void save(Review review);

    Slice<Review> requestMoreReview(@Param("id")Long id, Pageable pageable);
}
