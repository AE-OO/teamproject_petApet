package com.teamproject.petapet.domain.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.web.product.fileupload.UploadFile;
import com.teamproject.petapet.web.product.reviewdto.ReviewDTO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 박채원 22.10.02 작성
 */

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"member", "product","reviewImg"})
@EntityListeners(value = {AuditingEntityListener.class})

public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column(length = 45, nullable = false)
    private String reviewTitle;

    @Column(columnDefinition = "text not null")
    private String reviewContent;

    @CreationTimestamp
    @Column
    private LocalDateTime reviewDate;

    @Column(length = 1)
    private Long reviewRating;

    @ElementCollection
    @CollectionTable(name = "ReviewImg", joinColumns = @JoinColumn(name = "reviewImg", referencedColumnName = "reviewId"))
    private List<UploadFile> reviewImg;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    public static ReviewDTO toReviewDTO(Review review){
        return ReviewDTO.builder().reviewImg(review.getReviewImg())
                .reviewRating(review.getReviewRating())
                .reviewDate(review.getReviewDate())
                .reviewContent(review.getReviewContent())
                .reviewTitle(review.getReviewTitle())
                .reviewMember(review.getMember().getMemberId())
                .reviewProduct(review.getProduct().getProductName())
                .build();
    }
}
