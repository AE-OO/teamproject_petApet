package com.teamproject.petapet.web.product.coupon.coupondtos;

import com.querydsl.core.annotations.QueryProjection;
import com.teamproject.petapet.domain.product.ProductType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CouponBoxDTO {

    private Long couponBoxId;

    private boolean isUsed;

    private LocalDateTime expirationDate;

    private String memberId;

    private Long couponId;

    private String couponName;

    private String couponAcceptType;

    private Long couponDiscRate;

    private Long couponAcceptPrice;

    @QueryProjection
    public CouponBoxDTO(Long couponBoxId, boolean isUsed, LocalDateTime expirationDate, String memberId, Long couponId, String couponName, String couponAcceptType, Long couponDiscRate,Long couponAcceptPrice) {
        this.couponBoxId = couponBoxId;
        this.isUsed = isUsed;
        this.expirationDate = expirationDate;
        this.memberId = memberId;
        this.couponId = couponId;
        this.couponName = couponName;
        this.couponAcceptType = ProductType.valueOf(couponAcceptType).getProductCategory();
        this.couponDiscRate = couponDiscRate;
        this.couponAcceptPrice = couponAcceptPrice;
    }
}
