package com.teamproject.petapet.web.product.service;

import com.teamproject.petapet.domain.product.Review;
import com.teamproject.petapet.web.product.fileupload.UploadFile;
import com.teamproject.petapet.web.product.productdtos.ReviewInsertDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface ReviewService {

    Long countReviewByProduct(Long productId);

    Optional<Review> findById(Long reviewId);

    void save(Review review);

    Slice<Review> requestMoreReview(@Param("id") Long id, Pageable pageable);

    boolean existByReviewHistory(Long productId, String memberId);

    Optional<Review> findOne(Long productId, String memberId);
}
