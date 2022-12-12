package com.teamproject.petapet.web.product.productdtos;

import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.web.product.fileupload.UploadFile;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ProductMainPageListDTO {
    private Long productId;
    private String productName;
    private Long productPrice;
    private Long productRating;
    private String productDiv;
    private UploadFile productImg;
    private Integer productReview;

    public static List<ProductMainPageListDTO> getProductMainPageListDTOS(Page<Product> productList) {
        return productList.stream().map(m -> ProductMainPageListDTO.builder().productName(m.getProductName())
                .productImg(m.getProductImg().get(0))
                .productId(m.getProductId())
                .productRating(m.getProductRating())
                .productReview(m.getReview().size())
                .productDiv(m.getProductDiv().getProductCategory())
                .productPrice(m.getProductPrice())
                .build()).collect(Collectors.toList());
    }
}