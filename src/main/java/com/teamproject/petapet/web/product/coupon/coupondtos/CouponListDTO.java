package com.teamproject.petapet.web.product.coupon.coupondtos;

import com.querydsl.core.annotations.QueryProjection;
import com.teamproject.petapet.domain.product.ProductType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class CouponListDTO {

    private Long couponId;
    private String couponName;
    private String couponEndDate;
    private String couponAcceptType;
    private boolean couponActive;
    private Long couponDiscRate;

    @QueryProjection
    public CouponListDTO(Long couponId, String couponName, String couponEndDate, String couponAcceptType, boolean couponActive, Long couponDiscRate) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.couponEndDate = couponEndDate;
        this.couponAcceptType = ProductType.valueOf(couponAcceptType).getProductCategory();;
        this.couponActive = couponActive;
        this.couponDiscRate = couponDiscRate;
    }
}
