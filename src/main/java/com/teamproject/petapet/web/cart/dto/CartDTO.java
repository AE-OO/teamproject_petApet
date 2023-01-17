package com.teamproject.petapet.web.cart.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {

    private Long cartId;

    @NotNull(message = "수량을 입력하세요")
    private Long quantity;

}
