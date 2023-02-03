package com.teamproject.petapet.web.product.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.teamproject.petapet.domain.product.Review;
import com.teamproject.petapet.domain.product.repository.ReviewRepository;
import com.teamproject.petapet.web.product.fileupload.UploadFile;
import com.teamproject.petapet.web.product.reviewdto.ReviewDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.teamproject.petapet.domain.product.QReview.review;

@Service
@Transactional(readOnly = true)
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
    @Transactional
    public void save(Review review) {
        reviewRepository.save(review);
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    @Override
    @Transactional
    public void updateReview(Review review, String reviewTitle, String reviewContent, LocalDateTime reviewDate, Long reviewRating, List<UploadFile> reviewImg) {
        review.updateReview(reviewTitle, reviewContent, reviewDate, reviewRating, reviewImg);
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

    @Override
    public List<ReviewDTO> convertToReviewDTOList(Slice<Review> requestMoreReview) {
        return requestMoreReview.stream().map(m -> ReviewDTO.builder().reviewTitle(m.getReviewTitle())
                .reviewContent(m.getReviewContent())
                .reviewDate(m.getReviewDate())
                .reviewRating(m.getReviewRating())
                .reviewMember(m.getMember().getMemberId())
                .reviewProduct(m.getProduct().getProductName())
                .reviewImg(m.getReviewImg())
                .build()).collect(Collectors.toList());
    }
}
