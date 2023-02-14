package com.teamproject.petapet.web.util.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Data
@AllArgsConstructor
public class PaymentRequestDTO {
    private String productName;
    private Long totalPrice;
    private String memberName;
    private String memberPhoneNum;
    private String memberEmail;
    private String memberAddress;
    private Long totalQuantity;
    private String memberId;
    private List<Long> productIds;
    private List<Long> cartIds;
    private Long couponId;
    private String importId;
}
