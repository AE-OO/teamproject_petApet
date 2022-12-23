package com.teamproject.petapet.web.product.coupondtos;

import com.querydsl.core.annotations.QueryProjection;
import com.teamproject.petapet.domain.product.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
public class CouponDTO {
    private Long couponId;
    private LocalDateTime couponBeginDate;
    private LocalDateTime couponEndDate;
    private Long couponStock;
    private ProductType couponAcceptType;
    private boolean couponActive;
    private Long couponPriceDisc;
    private Long couponPercentDisc;

    @QueryProjection
    public CouponDTO(Long couponId, LocalDateTime couponBeginDate, LocalDateTime couponEndDate, Long couponStock, ProductType couponAcceptType, boolean couponActive, Long couponPriceDisc, Long couponPercentDisc) {
        this.couponId = couponId;
        this.couponBeginDate = couponBeginDate;
        this.couponEndDate = couponEndDate;
        this.couponStock = couponStock;
        this.couponAcceptType = couponAcceptType;
        this.couponActive = couponActive;
        this.couponPriceDisc = couponPriceDisc;
        this.couponPercentDisc = couponPercentDisc;
    }

}
