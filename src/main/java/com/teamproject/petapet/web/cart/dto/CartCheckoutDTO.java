package com.teamproject.petapet.web.cart.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartCheckoutDTO {
    private List<CartCheckoutDTO> cartCheckoutDTOList;
    private Long cartId;
    private String productName;
    private Long productPrice;
    private Long quantity;
}
