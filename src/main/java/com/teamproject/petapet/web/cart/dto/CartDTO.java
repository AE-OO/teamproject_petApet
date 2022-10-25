package com.teamproject.petapet.web.cart.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CartDTO {

    private Long cartId;
    private String memberId;
    private String productId;

    @NotNull(message = "수량을 입력하세요")
    private Long quantity;

}
