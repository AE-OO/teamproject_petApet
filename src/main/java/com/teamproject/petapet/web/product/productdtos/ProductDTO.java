package com.teamproject.petapet.web.product.productdtos;

import com.teamproject.petapet.domain.product.ProductType;
import lombok.Data;

/**
 * 박채원 22.12.15 작성
 */

@Data
public class ProductDTO {
    private Long productId;
    private String productName;
    private ProductType productDiv;
    private Long productPrice;
    private Long productStock;
    private String productStatus;
    private Long productReport;
    private Long totalBuy;
}
