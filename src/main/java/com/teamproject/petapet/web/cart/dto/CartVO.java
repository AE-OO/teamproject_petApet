package com.teamproject.petapet.web.cart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.teamproject.petapet.domain.product.Product;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CartVO {
//    @JsonProperty("product")
    private Long product;

//    @JsonProperty("quantity")
    @NotNull(message = "수량을 입력하세요")
    private Long quantity;
}
