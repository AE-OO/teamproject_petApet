package com.teamproject.petapet.web.cart.dto;

import com.teamproject.petapet.domain.product.Product;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
public class CartDTO {

    private Long cartId;

    private Product productId;

    @NotNull(message = "수량을 입력하세요")
    private Long quantity;

}
