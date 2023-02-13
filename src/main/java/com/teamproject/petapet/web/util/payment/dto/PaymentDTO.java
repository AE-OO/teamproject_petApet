package com.teamproject.petapet.web.util.payment.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaymentDTO {

    private List<Long> productIds;
    private List<Long> cartIds;
    private Long couponId;

    @Data
    public static class DirectPaymentDTO{
        private Long productId;
        private Long quantity;
        private Long couponId;
    }
}
