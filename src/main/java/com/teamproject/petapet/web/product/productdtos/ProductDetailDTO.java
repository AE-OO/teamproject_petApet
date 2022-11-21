package com.teamproject.petapet.web.product.productdtos;

import com.teamproject.petapet.domain.product.Review;
import com.teamproject.petapet.web.product.fileupload.UploadFile;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductDetailDTO {

    private Long productId;
    private String productContent;
    private List<UploadFile> productImg;
//    private List<Review> review;
    private String productName;
    private Long productPrice;
    private Long productRating;
    private Long productStock;
}
