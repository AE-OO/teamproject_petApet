package com.teamproject.petapet.web.buy.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BuyVO {

    private Long product;

    @NotNull(message = "수량을 입력하세요")
    private Long quantity;
}
