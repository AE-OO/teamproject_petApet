package com.teamproject.petapet.web.cart.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CartVO {

    private Long cartId;

    private Long product;

    @NotNull(message = "수량을 입력하세요")
    private Long quantity;
}
