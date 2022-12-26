package com.teamproject.petapet.web.product.productdtos;

import com.teamproject.petapet.domain.product.Product;
import lombok.Builder;
import lombok.Data;

/**
 * 박채원 22.12.15 작성
 */

@Data
@Builder
public class ProductDTO {
    private Long productId;
    private String productName;
    private String productDiv;
    private Long productPrice;
    private Long productStock;
    private String productStatus;
    private Long productReport;

    public static ProductDTO fromEntityForManageProduct(Product product){
        return ProductDTO.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productDiv(product.getProductDiv().getProductCategory())
                .productPrice(product.getProductPrice())
                .productStock(product.getProductStock())
                .productStatus(product.getProductStatus())
                .productReport(product.getProductReport())
                .build();
    }
}
