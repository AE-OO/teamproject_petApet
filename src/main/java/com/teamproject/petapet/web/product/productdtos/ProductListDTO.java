package com.teamproject.petapet.web.product.productdtos;

import lombok.Data;

@Data
public class ProductListDTO {
    private String productName;
    private Long productPrice;
    private Long productStock;
    private String productStatus;
    private String productDiv;
    private String productContent;
}
