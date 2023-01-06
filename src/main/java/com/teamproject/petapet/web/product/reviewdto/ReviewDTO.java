package com.teamproject.petapet.web.product.reviewdto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.teamproject.petapet.web.product.fileupload.UploadFile;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

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
