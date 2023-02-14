package com.teamproject.petapet.web.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartCheckoutDTO {
    private Long cartId;
    private Long productId;
    private String productName;
    private Long productPrice;
    private Long quantity;
    private Long cartTotalPrice;
}
