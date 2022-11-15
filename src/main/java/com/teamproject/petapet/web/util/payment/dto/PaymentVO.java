package com.teamproject.petapet.web.util.payment.dto;

import lombok.Getter;

@Getter
public class PaymentVO {
    private String buyAddress;
    private Long buyQuantity;
    private String buyMember;
    private Long buyProduct;
}
