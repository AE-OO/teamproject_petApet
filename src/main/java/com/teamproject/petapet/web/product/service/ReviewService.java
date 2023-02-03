package com.teamproject.petapet.web.product.service;

import com.teamproject.petapet.domain.product.Review;
import com.teamproject.petapet.web.product.fileupload.UploadFile;
import com.teamproject.petapet.web.product.reviewdto.ReviewDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReviewService {

    Long countReviewByProduct(Long productId);

    Optional<Review> findById(Long reviewId);

    void save(Review review);

    void deleteReview(Long reviewId);

    void updateReview(Review review, String reviewTitle, String reviewContent, LocalDateTime reviewDate, Long reviewRating, List<UploadFile> reviewImg);

    Slice<Review> requestMoreReview(@Param("id") Long id, Pageable pageable);

    boolean existByReviewHistory(Long productId, String memberId);

    Optional<Review> findOneByMemId(Long productId, String memberId);

    List<ReviewDTO> convertToReviewDTOList(Slice<Review> requestMoreReview);
}
