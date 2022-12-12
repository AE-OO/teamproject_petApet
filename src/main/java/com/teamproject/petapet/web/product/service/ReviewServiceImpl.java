package com.teamproject.petapet.web.product.service;

import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.domain.product.Review;
import com.teamproject.petapet.domain.product.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    @Override
    public Long countReviewByProduct(Product product) {
        return reviewRepository.countReviewByProduct(product);
    }

    @Override
    public void save(Review review) {
        reviewRepository.save(review);
    }

    @Override
    public Slice<Review> requestMoreReview(Long id, Pageable pageable) {
        return reviewRepository.requestMoreReview(id, pageable);
    }
}
