package com.teamproject.petapet.web.product.productdtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ReviewInsertDTO {

    private String reviewTitle;
    private String reviewContent;
    private Long reviewRating;
    private List<MultipartFile> reviewImg;
}