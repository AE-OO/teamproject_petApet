package com.teamproject.petapet.web.product.productdtos;

import com.teamproject.petapet.domain.product.ProductType;
import com.teamproject.petapet.domain.product.Review;
import com.teamproject.petapet.web.product.fileupload.UploadFile;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductListDTO {
    private Long productId;
    private String productName;
    private Long productPrice;
    private Long productRating;
    private ProductType productDiv;
    private List<UploadFile> productImg;
    private List<Review> review;
}
