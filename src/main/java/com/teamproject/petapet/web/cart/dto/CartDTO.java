package com.teamproject.petapet.web.cart.dto;

import com.teamproject.petapet.domain.product.Product;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {

    private Product productId;

    @NotNull(message = "수량을 입력하세요")
    private Long quantity;

}
