package com.teamproject.petapet.web.product.coupon.coupondtos;

import com.querydsl.core.annotations.QueryProjection;
import com.teamproject.petapet.domain.product.Coupon;
import com.teamproject.petapet.domain.product.ProductType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
public class CouponDTO {
    private Long couponId;
    @NotBlank(message = "쿠폰명을 입력하세요")
    private String couponName;
    @NotBlank(message = "만료일을 선택하세요")
    private String couponEndDate;
    @NotNull(message = "수량을 입력하세요")
    @Min(0)
    @Max(value = 9999, message = "0-9999까지의 값을 입력하세요")
    private Long couponStock;
    @NotNull(message = "적용 카테고리를 선택하세요")
    private ProductType couponAcceptType;
    @NotNull(message = "쿠폰 활성화 여부를 선택하세요")
    private boolean couponActive;
    @NotNull(message = "할인율을 입력하세요")
    @Min(value = 5, message = "5 이상의 값을 입력하세요")
    @Max(value = 99999, message = "5 자리의 숫자까지 입력하세요")
    private Long couponDiscRate;
    @NotBlank(message = "쿠폰 타입을 선택하세요")
    private String couponType;

    @QueryProjection
    public CouponDTO(Long couponId, String couponName, String couponEndDate, Long couponStock, ProductType couponAcceptType, boolean couponActive, Long couponDiscRate, String couponType) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.couponEndDate = couponEndDate;
        this.couponStock = couponStock;
        this.couponAcceptType = couponAcceptType;
        this.couponActive = couponActive;
        this.couponDiscRate = couponDiscRate;
        this.couponType = couponType;
    }

    public CouponDTO convertToDTO(Coupon coupon) {
        return CouponDTO.builder().couponActive(coupon.isCouponActive())
                .couponAcceptType(coupon.getCouponAcceptType())
                .couponEndDate(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(coupon.getCouponEndDate()))
                .couponStock(coupon.getCouponStock())
                .couponType(coupon.getCouponType())
                .couponDiscRate(coupon.getCouponDiscRate())
                .couponId(coupon.getCouponId())
                .couponName(coupon.getCouponName())
                .build();
    }

}
