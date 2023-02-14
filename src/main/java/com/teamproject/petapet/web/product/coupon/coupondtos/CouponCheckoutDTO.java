package com.teamproject.petapet.web.product.coupon.coupondtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponCheckoutDTO {
    private String acceptType;
    private Long acceptPrice;
}
