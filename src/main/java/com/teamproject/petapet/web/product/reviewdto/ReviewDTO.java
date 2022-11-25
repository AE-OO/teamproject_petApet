package com.teamproject.petapet.web.product.reviewdto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.domain.product.Review;
import com.teamproject.petapet.web.product.fileupload.UploadFile;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 */

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

}
