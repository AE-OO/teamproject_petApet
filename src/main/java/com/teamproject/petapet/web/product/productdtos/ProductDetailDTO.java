package com.teamproject.petapet.web.product.productdtos;

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
    private String productName;
    private String productSeller;
    private Long productPrice;
    private Long productStock;
    private Long productDiscountRate;
    private Long productUnitPrice;
    private Long productReviewCount;

}
