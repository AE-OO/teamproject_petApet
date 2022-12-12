package com.teamproject.petapet.web.product.reviewdto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.teamproject.petapet.domain.product.Review;
import com.teamproject.petapet.web.product.fileupload.UploadFile;
import lombok.*;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class ReviewDTO {

    private String reviewTitle;

    private String reviewContent;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime reviewDate;

    private Long reviewRating;

    private List<UploadFile> reviewImg;

    private String reviewMember;

    private String reviewProduct;
    public static List<ReviewDTO> getCollect(Slice<Review> requestMoreReview) {
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
