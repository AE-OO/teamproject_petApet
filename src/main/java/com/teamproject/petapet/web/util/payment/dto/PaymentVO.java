package com.teamproject.petapet.web.util.payment.dto;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
public class PaymentVO {

    private String uid;
    private String buyerEmail;
    private String buyAddress;
    private Long buyQuantity;
    private String buyMember;
    private Long buyProduct;
    @Data
    public static class PaymentResponseDTO {
        private String uid;
        private String buyerEmail;
        private String buyAddress;
        private Long buyQuantity;
        private Long couponId;
        private Long totalPrice;
        private List<Long> buyProducts;
        private List<Long> buyCartIds;
    }

}
