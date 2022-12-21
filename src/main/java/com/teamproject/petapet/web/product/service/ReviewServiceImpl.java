package com.teamproject.petapet.web.product.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.teamproject.petapet.domain.product.Review;
import com.teamproject.petapet.domain.product.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.teamproject.petapet.domain.product.QReview.review;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long countReviewByProduct(Long productId) {
        return reviewRepository.countReviewByProduct(productId);
    }

    @Override
    public Optional<Review> findById(Long reviewId) {
        return reviewRepository.findById(reviewId);
    }

    @Override
    public void save(Review review) {
        reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public Slice<Review> requestMoreReview(Long id, Pageable pageable) {
        return reviewRepository.requestMoreReview(id, pageable);
    }

    @Override
    public boolean existByReviewHistory(Long productId, String memberId) {
        Optional<Review> booleanResult = jpaQueryFactory.select(review)
                .from(review)
                .where(review.product.productId.eq(productId), review.member.memberId.eq(memberId))
                .fetch().stream().findAny();
        return booleanResult.isPresent();
    }

    @Override
    public Optional<Review> findOneByMemId(Long productId, String memberId) {
        return jpaQueryFactory.select(review)
                .from(review)
                .where(review.product.productId.eq(productId), review.member.memberId.eq(memberId))
                .stream().findAny();
    }
}
